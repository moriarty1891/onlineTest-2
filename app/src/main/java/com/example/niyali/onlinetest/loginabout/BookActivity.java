package com.example.niyali.onlinetest.loginabout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niyali.onlinetest.R;
import com.example.niyali.onlinetest.adapter.BookAdapter;
import com.example.niyali.onlinetest.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niyali on 17/4/15.
 */

public class BookActivity extends AppCompatActivity {

    private TextView titlebar;
    private List<Book> bookList=new ArrayList<Book>();
    private ListView listView;
    private BookAdapter adapter;
    private Book selectedBook;
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.book_show);
        titlebar=(TextView)findViewById(R.id.titlebar);
        titlebar.setText("书籍推荐");
        initBooks();
        listView=(ListView)findViewById(R.id.book_list_view);
        adapter=new BookAdapter(BookActivity.this,R.layout.each_book,bookList);
        listView.setAdapter(adapter);
        //如果listview中存在可点击的控件 会使listview item失去焦点
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBook=bookList.get(position);
                switch (selectedBook.getBookName())
                {
                    case "最新申论":
                        Intent shenlun=new Intent(BookActivity.this,TaobaoActivity.class);
                        startActivity(shenlun);
                        break;
                    case "最新行测":
                        break;
                    default:
                        break;
                }

            }
        });
        ExitApplication.getInstance().addActivity(this);
    }
    private void initBooks()
    {
        Book first=new Book("最新申论",R.mipmap.chat1);
        bookList.add(first);
        Book second=new Book("最新行测",R.mipmap.chat2);
        bookList.add(second);
        Book third=new Book("最新公考",R.mipmap.chat3);
        bookList.add(third);
        Book fourth=new Book("最新试卷",R.mipmap.chat4);
        bookList.add(fourth);
        Book fifth=new Book("最新行测",R.mipmap.chat5);
        bookList.add(fifth);
    }

}
