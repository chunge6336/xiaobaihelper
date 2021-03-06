package cn.xiaobaihome.xiaobaihelper.mvvm.model.remote


import android.content.Context
import cn.xiaobaihome.xiaobaihelper.BuildConfig
import cn.xiaobaihome.xiaobaihelper.helper.network.NetProvider
import cn.xiaobaihome.xiaobaihelper.helper.network.RequestHandler
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.exception.ApiException
import okhttp3.*
import java.io.IOException
import java.util.*

/**
 * 页面描述：BaseNetProvider
 *
 *
 * Created by ditclear on 2017/7/28.
 */

class BaseNetProvider(private val mContext: Context) : NetProvider {

    override fun configInterceptors(): Array<Interceptor>? {
        return null
    }

    override fun configHttps(builder: OkHttpClient.Builder) {

    }

    override fun configHandler(): RequestHandler {

        return HeaderHandler()
    }

    override fun configConnectTimeoutSecs(): Long {
        return CONNECT_TIME_OUT
    }

    override fun configReadTimeoutSecs(): Long {
        return READ_TIME_OUT
    }

    override fun configWriteTimeoutSecs(): Long {
        return WRITE_TIME_OUT
    }

    override fun configLogEnable(): Boolean {
        return BuildConfig.DEBUG
    }

    private val traceId: String
        get() = UUID.randomUUID().toString()

    inner class HeaderHandler : RequestHandler {

        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
            return request
        }

        @Throws(IOException::class)
        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
            var e: ApiException? = null
            when {
                401 == response.code -> throw ApiException("登录已过期,请重新登录!")
                403 == response.code -> e = ApiException("禁止访问!")
                404 == response.code -> e = ApiException("链接错误")
                503 == response.code -> e = ApiException("服务器升级中!")
                response.code > 300 -> {
                    val message = response.body!!.string()
                    e = if (Utils.check(message)) {
                        ApiException("服务器内部错误!")
                    } else {
                        ApiException(message)
                    }
                }
            }
            if (!Utils.check(e)) {
                throw e!!
            }
            return response
        }
    }

    companion object {

        val CONNECT_TIME_OUT: Long = 20
        val READ_TIME_OUT: Long = 180
        val WRITE_TIME_OUT: Long = 30
    }
}
