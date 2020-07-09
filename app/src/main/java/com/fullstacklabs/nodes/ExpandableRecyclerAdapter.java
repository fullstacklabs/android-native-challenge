package com.fullstacklabs.nodes;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstacklabs.nodes.models.Node;


public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder> {

    private SparseArray<Node> mNodes;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context mContext;

    public ExpandableRecyclerAdapter(SparseArray<Node> nodes) {
        this.mNodes = nodes;

        // Set initial expanded state to false
        for (int i = 0; i < nodes.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ExpandableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_expandable_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder viewHolder, final  int i) {
        viewHolder.setIsRecyclable(false);

        viewHolder.nodeName.setText(mNodes.get(i).getTitle());
        viewHolder.nodeUrl.setText(mNodes.get(i).getUrl());
        viewHolder.statusTextView.setText(mNodes.get(i).getStatus());

        if (mNodes.get(i).getStatus().equals(mContext.getString(R.string.online_status))) {
            viewHolder.statusView.setColor(StatusView.ONLINE_COLOR);
        } else if (mNodes.get(i).getStatus().equals(mContext.getString(R.string.loading_status))) {
            viewHolder.statusView.setColor(StatusView.LOADING_COLOR);
        } else {
            viewHolder.statusView.setColor(StatusView.OFFLINE_COLOR);
        }

        final boolean isExpanded = expandState.get(i);
        viewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        viewHolder.cardView.setOnClickListener(v -> {
            onClickButton(viewHolder.expandableLayout, viewHolder.chevronIcon,  i);
        });
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nodeName, nodeUrl, statusTextView;
        public CardView cardView;
        public View chevronIcon;
        public StatusView statusView;
        public LinearLayout expandableLayout;

        public ViewHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.card_view);
            nodeName = view.findViewById(R.id.node_name_text_view);
            nodeUrl = view.findViewById(R.id.node_url);
            statusTextView = view.findViewById(R.id.online_status);
            statusView = view.findViewById(R.id.circle_view);

            chevronIcon = view.findViewById(R.id.chevron_icon);
            expandableLayout = view.findViewById(R.id.expandable_layout);
        }
    }

    private void onClickButton(final LinearLayout expandableLayout, final View chevronIcon, final  int i) {
        if (expandableLayout.getVisibility() == View.VISIBLE) {
            createRotateAnimator(chevronIcon, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        } else {
            createRotateAnimator(chevronIcon, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
