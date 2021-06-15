package eu.kanade.tachiyomi.network

import eu.kanade.tachiyomi.source.online.dto.CheckTokenDto
import eu.kanade.tachiyomi.source.online.dto.LoginRequestDto
import eu.kanade.tachiyomi.source.online.dto.LoginResponseDto
import eu.kanade.tachiyomi.source.online.dto.LogoutDto
import eu.kanade.tachiyomi.source.online.dto.MangaListDto
import eu.kanade.tachiyomi.source.online.dto.ReadingStatusMapDto
import eu.kanade.tachiyomi.source.online.dto.RefreshTokenDto
import eu.kanade.tachiyomi.source.online.utils.MdApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MangaDexAuthService : MangaDexImageService {

    // New API
    @Headers("Cache-Control: no-cache")
    @POST(MdApi.login)
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

    @Headers("Cache-Control: no-cache")
    @POST(MdApi.logout)
    suspend fun logout(): Response<LogoutDto>

    @Headers("Cache-Control: no-cache")
    @GET(MdApi.checkToken)
    suspend fun checkToken(): Response<CheckTokenDto>

    @Headers("Cache-Control: no-cache")
    @POST(MdApi.refreshToken)
    suspend fun refreshToken(@Body request: RefreshTokenDto): Response<LoginResponseDto>

    @Headers("Cache-Control: no-cache")
    @GET("${MdApi.userFollows}?limit=100") //&includes[]=${MdConstants.coverArt}
    suspend fun userFollowList(@Query("offset") offset: Int): Response<MangaListDto>

    @Headers("Cache-Control: no-cache")
    @GET(MdApi.readingStatusForAllManga)
    suspend fun readingStatusForAllManga(): Response<ReadingStatusMapDto>
}