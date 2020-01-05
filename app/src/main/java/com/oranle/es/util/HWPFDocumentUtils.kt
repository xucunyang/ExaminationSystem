package com.oranle.es.util

import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.module.examination.ExamSheet
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.usermodel.*
import timber.log.Timber
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.NumberFormatException

/**
 *  使用HWPFDocument读文件
 */
class HWPFDocumentUtils {

    val docParseDebug = false;

    private fun log(o: Any) {
        if (docParseDebug)
            Timber.d(o.toString())
    }

    @Throws(Exception::class)
    suspend fun readDocAndSave(path: String): String {
        val `is` = FileInputStream(path)
        val doc = HWPFDocument(`is`)
        val range = doc.range

        //读表格
        log("---------读表格----------")
        readTable(range)

        val examSheet = getExamSheet(range)

        val msg = examSheet.saveToDB()

        log("---------examSheet---------- $examSheet")

        this.closeStream(`is`)
        return msg
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    private fun closeStream(`is`: InputStream?) {
        if (`is` != null) {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 输出书签信息
     *
     * @param bookmarks
     */
    private fun printInfo(bookmarks: Bookmarks) {
        val count = bookmarks.bookmarksCount
        log("书签数量：$count")
        var bookmark: Bookmark
        for (i in 0 until count) {
            bookmark = bookmarks.getBookmark(i)
            log("书签" + (i + 1) + "的名称是：" + bookmark.name)
            log("开始位置：" + bookmark.start)
            log("结束位置：" + bookmark.end)
        }
    }

    /**
     * 读表格
     * 每一个回车符代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落。
     *
     * @param range
     */
    private fun readTable(range: Range) {
        //遍历range范围内的table。
        val tableIter = TableIterator(range)
        var table: Table
        var row: TableRow
        var cell: TableCell
        while (tableIter.hasNext()) {
            table = tableIter.next()
            val rowNum = table.numRows()
            for (j in 0 until rowNum) {
                row = table.getRow(j)
                val cellNum = row.numCells()
                for (k in 0 until cellNum) {
                    cell = row.getCell(k)
                    //输出单元格的文本
                    log("第" + j + "行，第" + k + "列：" + cell.text().trim { it <= ' ' })
                }
            }
        }
    }

    /**
     * 读表格
     * 每一个回车符代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落。
     *
     * @param range
     */
    private fun getExamSheet(range: Range): ExamSheet {

        val content = StringBuilder()

        val tableIter = TableIterator(range)
        var table: Table
        var row: TableRow
        var cell: TableCell

        // exam sheet var
        val examSheetId = IDGenerator.id

        Timber.d("examSheetId $examSheetId")

        var title = ""
        var intro = ""
        val questionList: MutableList<SingleChoice> = ArrayList()
        var answerList: List<String?>? = mutableListOf()
        val reportRules = mutableListOf<ReportRule>()

        while (tableIter.hasNext()) {
            table = tableIter.next()
            val rowNum = table.numRows()

            // 题干是否读取完毕，（1. 问题， 2. 图片， 3. 媒体url）
            var questionReadComplete = false

            var question = ""
            var imgUrls = ""
            var mediaUrl = ""
            var options: String

            outloop@ for (rowIndex in 0 until rowNum) {
                row = table.getRow(rowIndex)
                val cellNum = row.numCells()

                // 报告规则所用变量
                var ruleStr = ""
                var size = ""
                var singleScore: String? = ""
                var wholeScore: String?
                var readRule = false
                loop@ for (cellIndex in 0 until cellNum) {

                    val cellContent = row.getCell(cellIndex).text().trim { it <= ' ' }

                    content.append(cellContent)
                    log("第" + rowIndex + "行，第" + cellIndex + "列：" + cellContent)

                    when {
                        rowIndex == 0 && cellIndex == 0 -> {
                            title = cellContent
                        }
                        rowIndex == 2 && cellIndex == 0 -> {
                            intro = cellContent
                        }
                        rowIndex > 3 -> {

                            if (isAnswer(cellContent) && (cellIndex == 0)) {
                                Timber.d("end index $cellContent")
                                answerList = getAnswerList(cellContent)
                                continue@outloop
                            }

                            if (isRule(cellContent) || readRule) {
                                Timber.d("rule index $cellContent")
                                readRule = cellIndex != 4

                                when (cellIndex) {
                                    0 -> Timber.d("rule begin $cellContent")
                                    1 -> ruleStr = cellContent
                                    2 -> size = cellContent
                                    3 -> singleScore = cellContent
                                    4 -> {
                                        wholeScore = cellContent
                                        val reportRule = ReportRule(
                                            sheetId = examSheetId,
                                            typeStr = ruleStr,
                                            size = size.toInt(),
                                            singleScore = parseNum(singleScore),
                                            wholeScore = parseNum(wholeScore)
                                        )
                                        reportRules.add(reportRule)
                                        Timber.d("rule end $reportRule")
                                    }
                                }
                                continue@loop
                            }

                            if (!questionReadComplete) {
                                when (cellIndex) {
                                    0 -> question = cellContent
                                    1 -> imgUrls = cellContent
                                    2 -> mediaUrl = cellContent
                                }
                                questionReadComplete = (cellNum == cellIndex + 1)
                            } else {
                                // 选项
                                if (cellIndex == 0) {
                                    options = cellContent

                                    // 读完加入
                                    questionList.add(
                                        SingleChoice(
                                            examSheetId = examSheetId,
                                            questionType = 1,
                                            question = question,
                                            questionImgUrls = imgUrls,
                                            mediaUrl = mediaUrl,
                                            options = options
                                        )
                                    )
                                }

                                questionReadComplete = cellNum != cellIndex + 1
                            }

                        }

                    }
                }
            }
        }

        return ExamSheet(
            id = examSheetId,
            title = title,
            introduction = intro,
            showIntroduction = true,
            showTip = true,
            singleChoiceList = questionList,
            answerList = answerList,
            reportRuleList = reportRules
        )
    }

    private fun getAnswerList(cellContent: String): List<String?>? =
        if (isAnswer(cellContent)) {
            val indexOf = cellContent.indexOf("：")
            val substring = cellContent.substring(indexOf + 1).replace(" ", "").toCharArray()
            val stringList = arrayOfNulls<String>(substring.size)
            substring.forEachIndexed { index: Int, c: Char ->
                stringList[index] = c.toString()
            }
            stringList.toList()
        } else {
            null
        }

    private fun isAnswer(cellContent: String) = cellContent.startsWith("答案：")

    private fun isRule(cellContent: String) = cellContent.startsWith("规则：")

    private fun parseNum(sizeStr: String?): Float {

        if (sizeStr == null || sizeStr.isBlank()) {
            return 0F
        } else {
            var size: Float
            try {
                size = sizeStr.toFloat()
            } catch (e: NumberFormatException) {
                size = 0F
                e.printStackTrace()
                Timber.w(e)
            }
            return size
        }
    }

    /**
     * 读列表
     *
     * @param range
     */
    private fun readList(range: Range) {
        val num = range.numParagraphs()
        var para: Paragraph
        for (i in 0 until num) {
            para = range.getParagraph(i)
            if (para.isInList) {
                log("list: " + para.text())
            }
        }
    }

    /**
     * 输出Range
     *
     * @param range
     */
    private fun printInfo(range: Range) {
        //获取段落数
        val paraNum = range.numParagraphs()
        log(paraNum)
        for (i in 0 until paraNum) {
            log("段落" + (i + 1) + "：" + range.getParagraph(i).text())
            if (i == paraNum - 1) {
                this.insertInfo(range.getParagraph(i))
            }
        }
        val secNum = range.numSections()
        log(secNum)
        var section: Section
        for (i in 0 until secNum) {
            section = range.getSection(i)
            log(section.marginLeft)
            log(section.marginRight)
            log(section.marginTop)
            log(section.marginBottom)
            log(section.pageHeight)
            log(section.text())
        }
    }

    /**
     * 插入内容到Range，这里只会写到内存中
     *
     * @param range
     */
    private fun insertInfo(range: Range) {
        range.insertAfter("Hello")
    }

    companion object {
        private val TAG = "HWPFDocumentUtils"
    }
}