package com.letsdogether.dogether;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rakeshk on 03-04-2018.
 */

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>{

    private List<IssuesModel> issuesList;
    private Context context;

    public IssuesAdapter(List<IssuesModel> issuesList, Context context){
        this.issuesList = issuesList;
        this.context = context;
    }

    @Override
    public IssuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_issues,parent,false);
        return new IssuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssuesViewHolder holder, int position) {

        IssuesModel issuesModel = issuesList.get(position);

        String strState = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mmm-yyy");
        Date date = new Date();

        if (issuesModel.getState().equals("open")) {
            strState = "opened";
            try {
                date = sdf.parse(issuesModel.created_at.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            strState = "closed";
            try {
                date = sdf.parse(issuesModel.closed_at.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.title.setText(issuesModel.getTitle());
        holder.comments.setText(issuesModel.getComments());
        holder.state.setText("#" + issuesModel.getNumber() + " " + strState + " on " + date.toString());

        holder.rlMain.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(issuesModel.getHtml_url()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    class IssuesViewHolder extends RecyclerView.ViewHolder {

        TextView title, state, comments;
        RelativeLayout rlMain;

        public IssuesViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            state = (TextView) itemView.findViewById(R.id.state);
            comments = (TextView) itemView.findViewById(R.id.comments);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
        }
    }
}