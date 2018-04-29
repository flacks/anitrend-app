package com.mxt.anitrend.view.fragment.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mxt.anitrend.R;
import com.mxt.anitrend.adapter.recycler.index.FeedAdapter;
import com.mxt.anitrend.base.custom.consumer.BaseConsumer;
import com.mxt.anitrend.model.entity.anilist.FeedList;
import com.mxt.anitrend.util.CompatUtil;
import com.mxt.anitrend.util.GraphUtil;
import com.mxt.anitrend.util.KeyUtil;
import com.mxt.anitrend.view.activity.detail.ProfileActivity;
import com.mxt.anitrend.view.fragment.list.FeedListFragment;
import com.mxt.anitrend.view.sheet.BottomSheetComposer;

/**
 * Created by max on 2018/03/24.
 * MessageFeedFragment
 */

public class MessageFeedFragment extends FeedListFragment {

    private long userId;
    private @KeyUtil.MessageType int messageType;

    public static MessageFeedFragment newInstance(Bundle params, @KeyUtil.MessageType int messageType) {
        Bundle args = new Bundle(params);
        args.putInt(KeyUtil.arg_message_type, messageType);
        MessageFeedFragment fragment = new MessageFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageType = getArguments().getInt(KeyUtil.arg_message_type);
            userId = getArguments().getLong(KeyUtil.arg_userId);
        }
        isMenuDisabled = true; isFeed = false;
    }

    @Override
    protected void updateUI() {
        if(mAdapter == null)
            mAdapter = new FeedAdapter(model, getContext(), messageType);
        super.updateUI();
    }

    @Override
    public void makeRequest() {
        queryContainer = GraphUtil.getDefaultQuery(true);
        queryContainer.putVariable(KeyUtil.arg_page, getPresenter().getCurrentPage())
                .putVariable(messageType == KeyUtil.MESSAGE_TYPE_INBOX ? KeyUtil.arg_userId : KeyUtil.arg_messengerId, userId);
        getViewModel().getParams().putParcelable(KeyUtil.arg_graph_params, queryContainer);
        getViewModel().requestData(KeyUtil.FEED_MESSAGE_REQ, getContext());
    }

    @Override
    public void onItemClick(View target, FeedList data) {
        Intent intent;
        switch (target.getId()) {
            case R.id.messenger_avatar:
                if (data.getMessenger() != null) {
                    intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(KeyUtil.arg_id, data.getMessenger().getId());
                    CompatUtil.startRevealAnim(getActivity(), target, intent);
                }
                break;
            case R.id.recipient_avatar:
                if (data.getRecipient() != null) {
                    intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(KeyUtil.arg_id, data.getRecipient().getId());
                    CompatUtil.startRevealAnim(getActivity(), target, intent);
                }
                break;
            case R.id.widget_edit:
                mBottomSheet = new BottomSheetComposer.Builder().setUserActivity(data)
                        .setRequestMode(KeyUtil.MUT_SAVE_MESSAGE_FEED)
                        .setUserModel(data.getRecipient())
                        .setTitle(R.string.edit_status_title)
                        .build();
                showBottomSheet();
                break;
            default:
                super.onItemClick(target, data);
                break;
        }
    }
}
