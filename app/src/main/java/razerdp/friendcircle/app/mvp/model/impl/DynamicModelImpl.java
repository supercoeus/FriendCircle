package razerdp.friendcircle.app.mvp.model.impl;

import razerdp.friendcircle.app.https.base.BaseResponse;
import razerdp.friendcircle.app.https.request.DynamicAddCommentRequest;
import razerdp.friendcircle.app.https.request.DynamicAddPraiseRequest;
import razerdp.friendcircle.app.https.request.DynamicCancelPraiseRequest;
import razerdp.friendcircle.app.https.request.DynamicDeleteCommentRequest;
import razerdp.friendcircle.app.https.request.RequestType;
import razerdp.friendcircle.app.interfaces.BaseResponseListener;
import razerdp.friendcircle.app.interfaces.DynamicResultCallBack;
import razerdp.friendcircle.app.mvp.model.CommentModel;
import razerdp.friendcircle.app.mvp.model.PraiseModel;

/**
 * Created by 大灯泡 on 2016/3/17.
 * mvp - model
 * 复杂逻辑的操作/请求/耗时等，处理后的数据提供
 *
 * 若过于复杂的操作，可能需要接口来松耦合
 */
public class DynamicModelImpl implements BaseResponseListener, PraiseModel,CommentModel {
    private DynamicResultCallBack callBack;

    public DynamicModelImpl(DynamicResultCallBack callBack) {
        this.callBack = callBack;
    }

    //=============================================================request
    private DynamicAddPraiseRequest mDynamicAddPraiseRequest;
    private DynamicCancelPraiseRequest mDynamicCancelPraiseRequest;

    private DynamicAddCommentRequest mDynamicAddCommentRequest;
    private DynamicDeleteCommentRequest mDynamicDeleteCommentRequest;

    //=============================================================public methods
    @Override
    public void addPraise(int currentDynamicPos, long userid, long dynamicid) {
        if (mDynamicAddPraiseRequest == null) {
            mDynamicAddPraiseRequest = new DynamicAddPraiseRequest();
            mDynamicAddPraiseRequest.setOnResponseListener(this);
            mDynamicAddPraiseRequest.setRequestType(RequestType.ADD_PRAISE);
        }
        mDynamicAddPraiseRequest.setCurrentDynamicPos(currentDynamicPos);
        mDynamicAddPraiseRequest.userid = userid;
        mDynamicAddPraiseRequest.dynamicid = dynamicid;
        mDynamicAddPraiseRequest.execute();
    }

    @Override
    public void cancelPraise(int currentDynamicPos, long userid, long dynamicid) {
        if (mDynamicCancelPraiseRequest == null) {
            mDynamicCancelPraiseRequest = new DynamicCancelPraiseRequest();
            mDynamicCancelPraiseRequest.setOnResponseListener(this);
            mDynamicCancelPraiseRequest.setRequestType(RequestType.CANCEL_PRAISE);
        }
        mDynamicCancelPraiseRequest.setCurrentDynamicPos(currentDynamicPos);
        mDynamicCancelPraiseRequest.userid = userid;
        mDynamicCancelPraiseRequest.dynamicid = dynamicid;
        mDynamicCancelPraiseRequest.execute();
    }


    @Override
    public void addComment(int currentDynamicPos, long dynamicid, long userid, long replyid, String content) {
        if (mDynamicAddCommentRequest == null) {
            mDynamicAddCommentRequest = new DynamicAddCommentRequest();
            mDynamicAddCommentRequest.setOnResponseListener(this);
            mDynamicAddCommentRequest.setRequestType(RequestType.ADD_COMMENT);
        }
        mDynamicAddCommentRequest.setCurrentDynamicPos(currentDynamicPos);
        mDynamicAddCommentRequest.userid = userid;
        mDynamicAddCommentRequest.dynamicid = dynamicid;
        mDynamicAddCommentRequest.replyid=replyid;
        mDynamicAddCommentRequest.content=content;

        mDynamicAddCommentRequest.post();

    }

    @Override
    public void delComment(int currentDynamicPos, long dynamicid, long userid, long commentid) {
        if (mDynamicDeleteCommentRequest == null) {
            mDynamicDeleteCommentRequest = new DynamicDeleteCommentRequest();
            mDynamicDeleteCommentRequest.setOnResponseListener(this);
            mDynamicDeleteCommentRequest.setRequestType(RequestType.DEL_COMMENT);
        }
        mDynamicDeleteCommentRequest.setCurrentDynamicPos(currentDynamicPos);
        mDynamicDeleteCommentRequest.userid = userid;
        mDynamicDeleteCommentRequest.dynamicid = dynamicid;
        mDynamicDeleteCommentRequest.commentid=commentid;

        mDynamicDeleteCommentRequest.execute();
    }

    //=============================================================request methods
    @Override
    public void onStart(BaseResponse response) {

    }

    @Override
    public void onStop(BaseResponse response) {

    }

    @Override
    public void onFailure(BaseResponse response) {

    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 200) {
            if (callBack != null) {
                callBack.onResultCallBack(response);
            }
        }
    }
}
