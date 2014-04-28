package com.example.nutrition252;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ListAdapter_Expandable extends BaseExpandableListAdapter {

	private Context getContext;
	private List<String> getListHeaderData; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> getListChildData;

	public ListAdapter_Expandable(Context context, List<String> listDataHeader,HashMap<String, List<String>> listChildData) {
		this.getContext = context;
		this.getListHeaderData = listDataHeader;
		this.getListChildData = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.getListChildData.get(this.getListHeaderData.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.getContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.child_list, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.tvListItem);

		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.getListChildData.get(this.getListHeaderData.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.getListHeaderData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.getListHeaderData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.getContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_list, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvListhead);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}