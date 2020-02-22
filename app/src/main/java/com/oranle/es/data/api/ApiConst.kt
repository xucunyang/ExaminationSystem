package com.oranle.es.data.api

import com.oranle.es.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

const val RELEASE_IP = "http://47.99.148.238/"
const val DEGUG_IP = RELEASE_IP
var BASE_URL = if (BuildConfig.DEBUG) RELEASE_IP else RELEASE_IP

const val CONTENT_TYPE_JSON = "Content-Type: application/json"
const val ACCEPT_TYPE_JSON = "Accept: application/json"
const val ACCEPT_TYPE_STREAM = "Accept: application/octet-stream"


const val ROOT_PATH = "${RELEASE_IP}edu/"

const val CHECK_UPGRADE = "${ROOT_PATH}config"

interface CheckUpgrade {

    @Headers(CONTENT_TYPE_JSON, ACCEPT_TYPE_STREAM)
    @GET(CHECK_UPGRADE)
    fun checkAsync(): Deferred<String?>

}

const val GET_LOG = "${ROOT_PATH}update.log"

interface UpgradeLog {

    @Headers(CONTENT_TYPE_JSON, ACCEPT_TYPE_JSON)
    @GET(GET_LOG)
    fun getLogAsync(): Deferred<String?>

}

//interface Login {
//
//    @Headers(CONTENT_TYPE_JSON, ACCEPT_TYPE_JSON)
//    @POST(LOGIN)
//    fun login(
//        @Body body: RequestBody,
//        @Query(APP_ID_KEY) appId: String = APP_ID_VALUE
//    ): Observable<ResponseBody>
//
//}
//
//const val QUERY_WAIT_TRANSPORT_ORDER = "${ROOT_PATH}services/shipper/order/queryOrder"
//
//enum class OrderState(val value: Int) {
//    WaitTransport(1),
//    Transporting(2),
//    TransportComplete(3),
//    AllState(4)
//}
//
//interface WaitTransportOrder {
//
//    @Headers(CONTENT_TYPE_JSON, ACCEPT_TYPE_JSON)
//    @GET(QUERY_WAIT_TRANSPORT_ORDER)
//    fun queryAsync(
//        @Query("status") status: Int,
//        @Query("page") page: Int,
//        @Query("rows") rows: Int,
//        @Query(APP_ID_KEY) appId: String = APP_ID_VALUE,
//        @Query(TOKEN) token: String = SpUtils.getToken()
//    ): Deferred<OrderQueryResponse>
//
//}







