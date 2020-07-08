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
    private final SparseArray<Node> nodes;
    public LayoutInflater inflater;
    public Activity activity;

    public ExpandableListAdapter(Activity act, SparseArray<Node> nodes) {
        activity = act;
        this.nodes = nodes;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return nodes.get(groupPosition).blocks.get(childPosition);
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
            convertView = inflater.inflate(R.layout.list_view_row_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return nodes.get(groupPosition).blocks.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return nodes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return nodes.size();
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
            convertView = inflater.inflate(R.layout.list_view_row, null);
        }
        Node nodes = (Node) getGroup(groupPosition);
        ((CheckedTextView) convertView.findViewById(R.id.title_text_view)).setText(nodes.title);
        ((CheckedTextView) convertView.findViewById(R.id.title_text_view)).setChecked(isExpanded);


        ((TextView) convertView.findViewById(R.id.online_status)).setText(nodes.status);
        ((TextView) convertView.findViewById(R.id.node_url)).setText(nodes.url);

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
