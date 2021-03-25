package com.app.alram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.alram.DetailsActivity;
import com.app.alram.Modle.Alarms;
import com.app.alram.R;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {

    private Context context;
    private List<Alarms> objects;

    public AlarmAdapter(Context context, List<Alarms> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        Alarms data = objects.get(i);

        myViewHolder.tv_name_task.setText(data.getTitle_alarm());
        myViewHolder.tv_level.setText("Level : "+data.getLevel());
        myViewHolder.tv_date.setText(data.getDate());
        myViewHolder.tv_time.setText(data.getTime());

        myViewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("name_task", data.getTitle_alarm());
            intent.putExtra("level", data.getLevel());
            intent.putExtra("date", data.getDate());
            intent.putExtra("time", data.getTime());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_task, tv_level, tv_date, tv_time;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_task = itemView.findViewById(R.id.tv_name_alarm);
            tv_level = itemView.findViewById(R.id.tv_level);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}
