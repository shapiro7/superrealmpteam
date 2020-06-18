package com.example.newmemeo;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button btnAdd;

    String NotiMain;
    String NotiSub;

    private Button create;
    private Button remove;

    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        create = findViewById(R.id.alamCreate);
        remove = findViewById(R.id.alamRemove);

        create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                createNotification();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeNotification();
            }
        });


        dbHelper = new SQLiteHelper(MainActivity.this);
        memoList = dbHelper.selectAll();


        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);
        btnAdd=findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메모 작성
                Intent intent = new Intent(MainActivity.this,AddActivity.class);;
                startActivityForResult(intent,0);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 0){

            if (resultCode != Activity.RESULT_OK) {
                return;
            }

            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");
            NotiMain = data.getStringExtra("main");
            NotiSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain,strSub,0);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();

            dbHelper.insertMemo(memo);

        }
    }

    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(NotiSub);
        builder.setContentText(NotiMain);

        builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        notificationManager.notify(1, builder.build());
    }

    private void removeNotification() {

        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }



    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<Memo> listdata;

        public RecyclerAdapter(List<Memo> listdata){
            this.listdata=listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            Memo memo = listdata.get(i);

            itemViewHolder.maintext.setTag(memo.getSeq());

            itemViewHolder.maintext.setText(memo.getMiantext());
            itemViewHolder.subtext.setText(memo.getSubetxt());

            if(memo.getIsdone()==0){
                itemViewHolder.img.setBackgroundColor(Color.LTGRAY);
            }
            else{
                itemViewHolder.img.setBackgroundColor(Color.GREEN);
            }
        }

        void addItem(Memo memo){
            listdata.add(memo);
        }

        void removeItem(int position){
            listdata.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            private TextView maintext;
            private TextView subtext;
            private ImageView img;


            public ItemViewHolder(@NonNull View itemView){
                super(itemView);

                maintext = itemView.findViewById(R.id.item_maintext);
                subtext = itemView.findViewById(R.id.item_subtext);
                img = itemView.findViewById(R.id.item_image);


                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        int position = getAdapterPosition();
                        int seq = (int)maintext.getTag();

                        if(position!=recyclerView.NO_POSITION){
                            dbHelper.deleteMemo(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                        }
                        return false;
                    }
                });

            }
        }
    }
}
