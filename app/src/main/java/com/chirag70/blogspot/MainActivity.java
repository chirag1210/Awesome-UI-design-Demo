package com.chirag70.blogspot;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.chirag70.blogspot.helper.SimpleItemTouchHelperCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ItemAdapter.OnStartDragListener{

    private ItemTouchHelper mItemTouchHelper;
    private int nu=0;
    TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // setTabs();

        TextView tvDate=(TextView)findViewById(R.id.tvDate);
        TextView tvDay=(TextView)findViewById(R.id.tvDay);
         tvNumber=(TextView)findViewById(R.id.tvNumber);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        assert tvDate!=null;
        assert  tvDay!=null;
        tvDate.setTypeface(Typefaces.getRobotoBlack(this));
        tvDay.setTypeface(Typefaces.getRobotoBlack(this));
        tvDate.setText( dateformat.format(c.getTime()).toUpperCase());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        final ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(),this,tvNumber);
        recyclerView.setAdapter(itemAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        loadItems();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab!=null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item item = new Item();
                nu= ItemAdapter.itemList.size();
                nu++;
                item.setItemName("item"+nu);
                llm.scrollToPositionWithOffset(0, dpToPx(56));
                itemAdapter.addItem(0, item);
            }
        });
    }

    @Override
    public void onDestroy()
    {
      super.onDestroy();
        ItemAdapter.itemList.clear();

    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }


    private void loadItems()
    {
        //Initial items
        for(int i=10;i>0;i--)
        {
            Item item = new Item();
            item.setItemName("item"+i);
            ItemAdapter.itemList.add(item);
        }
        tvNumber.setText(String.valueOf(ItemAdapter.itemList.size()));
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
