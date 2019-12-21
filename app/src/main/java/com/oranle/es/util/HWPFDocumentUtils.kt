package com.oranle.es.util

import com.oranle.es.module.examination.ExamSheet
import com.oranle.es.data.entity.SingleChoice

import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.usermodel.Bookmark
import org.apache.poi.hwpf.usermodel.Bookmarks
import org.apache.poi.hwpf.usermodel.Paragraph
import org.apache.poi.hwpf.usermodel.Range
import org.apache.poi.hwpf.usermodel.Section
import org.apache.poi.hwpf.usermodel.Table
import org.apache.poi.hwpf.usermodel.TableCell
import org.apache.poi.hwpf.usermodel.TableIterator
import org.apache.poi.hwpf.usermodel.TableRow

import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

import timber.log.Timber

//使用HWPFDocument读文件
class HWPFDocumentUtils {

    private fun log(o: Any) {
        Timber.d(o.toString())
    }

    @Throws(Exception::class)
    suspend fun readDocAndSave(path: String): String {
        val text: String
        val `is` = FileInputStream(path)
        val doc = HWPFDocument(`is`)
        val range = doc.range

        //读表格
        Timber.d("---------读表格----------")
        text = this.readTable(range)

        val examSheet = getExamSheet(range)

        val msg = examSheet.saveToDB()

        Timber.d("---------examSheet---------- $examSheet")

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
    private fun readTable(range: Range): String {
        val content = StringBuilder()
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
                    content.append(cell.text().trim { it <= ' ' })
                    log("第" + j + "行，第" + k + "列：" + cell.text().trim { it <= ' ' })
                }
            }
        }
        return content.toString()
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
        var title = ""
        var intro = ""
        val questionList: MutableList<SingleChoice> = ArrayList()
        var answerList: List<String?>? = mutableListOf()

        while (tableIter.hasNext()) {
            table = tableIter.next()
            val rowNum = table.numRows()

            // 题干是否读取完毕，（1. 问题， 2. 图片， 3. 媒体url）
            var questionReadComplete = false

            var question = ""
            var imgUrls = ""
            var mediaUrl = ""
            var options = ""
            for (rowIndex in 0 until rowNum) {
                row = table.getRow(rowIndex)
                val cellNum = row.numCells()

                for (cellIndex in 0 until cellNum) {

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

                            if (rowIndex == rowNum)
                                answerList = getAnswerList(cellContent)

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
            answerList = answerList
        )
    }

    private fun getAnswerList(cellContent: String): List<String?>? =
        if (cellContent.contains("答案：")) {
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