package gabriel.com.nwebtoon_android.network;


import java.util.List;

import gabriel.com.nwebtoon_android.model.adList;
import gabriel.com.nwebtoon_android.model.commentList;
import gabriel.com.nwebtoon_android.model.webtoonInfo;
import gabriel.com.nwebtoon_android.model.webtoonListSearch;
import gabriel.com.nwebtoon_android.model.webtoonListDetail;
import gabriel.com.nwebtoon_android.model.webtoonListMyFavorite;
import gabriel.com.nwebtoon_android.model.webtoonShow;
import retrofit.Callback;
import gabriel.com.nwebtoon_android.model.webtoonListDay;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ServerInterface {

   @GET("/webtoonListDay")
   void getwebtoonLIstDays(@Query("day") int day, Callback<List<webtoonListDay>> callback) ;

   @GET("/detailWebtoonList")
   void getdetailWebtoonList(@Query("toonId") String toonId, Callback<List<webtoonListDetail>> callback) ;

   @GET("/favoriteWebtoonList")
   void getfavoriteWebtoonList(@Query("userId") String userId, Callback<List<webtoonListMyFavorite>> callback) ;

   @GET("/cutImageList")
   void getcutImageList(@Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonShow>> callback) ;

   @GET("/commentList")
   void getCommentList(@Query("commentType") int commentType, @Query("dtId") String dtId, @Query("ctId") String ctId, @Query("toonId") String toonId, @Query("pageNum") String pageNum, Callback<List<commentList>> callback);

   @GET("/advertisementList")
   void getAdvertisementList(Callback<List<adList>> callback);

  // @GET("/HeaderWebtoonFavorite")
  // public void getHDFavorite(@Query("userId") String userId, @Query("toonId") String toonId, Callback<>);

   @GET("/HeaderToonLike")
   void getHeaderToonLike(@Query("toonId") String toonId, @Query("userId") String userId, Callback<List<webtoonInfo>> callback);
   @GET("/HeaderToonLikeNum")
   void getHeaderToonLikeNum(@Query("toonId") String toonId, Callback<List<webtoonInfo>> callback);
   @GET("/updateHeaderToonLike")
   void updateHeaderToonLike(@Query("like") boolean like, @Query("userId") String userId, @Query("toonId") String toonId, Callback<List<webtoonInfo>> callback);

   @GET("/HeaderWebtoonFavorite")
   void getHeaderFavirite(@Query("userId") String userId, @Query("toonId") String toonId, Callback<List<webtoonInfo>> callback);
   @GET("/insertFavoriteWebtoon")
   void insertHeaderFavirite(@Query("toonId") String toonId, @Query("userId") String userId, Callback<List<webtoonListDetail>> callback);
   @GET("/deleteFavoriteWebtoon")
   void deleteHeaderFavirite(@Query("toonId") String toonId, @Query("userId") String userId, Callback<List<webtoonListDetail>> callback);

   @GET("/detailToonLike")
   void getdetailToonLike(@Query("toonId") String toonId, @Query("dtId") String dtId, @Query("userId") String userId, Callback<List<webtoonInfo>> callback);
   @GET("/detailToonLikeNum")
   void getdetailToonLikeNum(@Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonInfo>> callback);
   @GET("/updateDetailToonLike")
   void updateDetailToonLike(@Query("like") boolean like, @Query("userId") String userId, @Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonInfo>> callback);

   @GET("/getdetailToonRate")
   void getRateDetailToon(@Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonInfo>> callback);
   @GET("/insertPointDetailToon")
   void insertPointDetailToon(@Query("point") int point, @Query("userId") String userId, @Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonInfo>> callback);
   @GET("/getPointDetailToon")
   void getPointDetailToon(@Query("userId") String userId, @Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<webtoonInfo>> callback);

   @GET("/bestCommentList")
   void getBestCommentList(@Query("pageNum") int pageNum, @Query("toonId") String toonId, @Query("dtId") String dtId, Callback<List<commentList>> callback);

   @POST("/insertComment")
   @Headers({ "Content-Type: application/x-www-form-urlencoded; charset=utf-8" })
   void postInsertComment(@Query("cmtId") String cmtId, @Query("content") String content, @Query("dtId") String dtId, @Query("ctId") String ctId, @Query("cmtTp") String cmtTp, @Query("userId") String userId, @Query("toonId") String toonId, @Query("pageNum") String pageNum, Callback<List<commentList>> callback);

   @GET("/webtoonList")
   void getwebtoonList(@Query("string") String string, Callback<List<webtoonListSearch>> callback);
}