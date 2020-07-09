package com.fullstacklabs.nodes;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.fullstacklabs.nodes.models.Node;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final SparseArray<Node> mNodes;
    public LayoutInflater mInflater;
    public Activity mActivity;

    public ExpandableListAdapter(Activity activity, SparseArray<Node> nodes) {
        mActivity = activity;
        this.mNodes = nodes;
        mInflater = activity.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mNodes.get(groupPosition).blocks.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view_row_details, null);
        }

        text = (TextView) convertView.findViewById(R.id.block_text_view);
        text.setText(children);

        convertView.setOnClickListener(v -> {
            Toast.makeText(mActivity, children, Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mNodes.get(groupPosition).blocks.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mNodes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mNodes.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view_row, null);
        }
        Node node = (Node) getGroup(groupPosition);

        CheckedTextView checkTextView = (CheckedTextView) convertView.findViewById(R.id.title_text_view);
        TextView statusTextView = (TextView) convertView.findViewById(R.id.online_status);
        TextView nodeUrl = (TextView) convertView.findViewById(R.id.node_url);
        StatusView statusView = (StatusView) convertView.findViewById(R.id.circle_view);


        checkTextView.setText(node.getTitle());
        checkTextView.setChecked(isExpanded);
        statusTextView.setText(node.getStatus());
        nodeUrl.setText(node.getUrl());

        if (node.getStatus().equals(mActivity.getString(R.string.online_status))) {
            statusView.setColor(StatusView.ONLINE_COLOR);
        } else {
            statusView.setColor(StatusView.OFFLINE_COLOR);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
