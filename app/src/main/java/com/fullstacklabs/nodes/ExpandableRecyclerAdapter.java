package com.fullstacklabs.nodes;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstacklabs.nodes.databinding.ListExpandableViewBinding;
import com.fullstacklabs.nodes.models.Node;


public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder> {

    private SparseArray<Node> mNodes;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public ExpandableRecyclerAdapter(SparseArray<Node> nodes) {
        this.mNodes = nodes;
    }

    @Override
    public ExpandableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ListExpandableViewBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_expandable_view, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder viewHolder, final  int i) {
        if (mNodes.get(i).getStatus().equals(mContext.getString(R.string.online_status))) {
            viewHolder.statusView.setColor(StatusView.ONLINE_COLOR);
        } else if (mNodes.get(i).getStatus().equals(mContext.getString(R.string.loading_status))) {
            viewHolder.statusView.setColor(StatusView.LOADING_COLOR);
        } else {
            viewHolder.statusView.setColor(StatusView.OFFLINE_COLOR);
        }

        viewHolder.binding.expandableLayout.setVisibility(View.GONE);

        viewHolder.binding.setIsExpanded(false);
        viewHolder.binding.setNode(mNodes.get(i));
        viewHolder.binding.setHandlers(view -> {
            if (viewHolder.binding.getIsExpanded()) {
                createRotateAnimator(viewHolder.binding.chevronIcon, 180f, 0f).start();
                viewHolder.binding.setIsExpanded(false);
            } else {
                createRotateAnimator(viewHolder.binding.chevronIcon, 0f, 180f).start();
                viewHolder.binding.setIsExpanded(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public StatusView statusView;
        private final ListExpandableViewBinding binding;

        public ViewHolder(final ListExpandableViewBinding itemBinding) {
            super(itemBinding.getRoot());
            statusView = itemBinding.getRoot().findViewById(R.id.circle_view);
            this.binding = itemBinding;
        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
