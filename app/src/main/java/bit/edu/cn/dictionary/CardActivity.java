package bit.edu.cn.dictionary;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import bit.edu.cn.dictionary.adapter.CardAdapter;
import bit.edu.cn.dictionary.db.SaveWord;
import bit.edu.cn.dictionary.menu.SpaceItemDecoration;

import static bit.edu.cn.dictionary.ListActivity.ListAdapter;
import static bit.edu.cn.dictionary.ListActivity.ListWord;

public class CardActivity extends AppCompatActivity {
    public CardAdapter CardAdapter;
    public SaveWord cardword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorDark));
        }
       setStatusBarLightMode();

        setContentView(R.layout.activity_card);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        int space=20;
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        CardAdapter=new CardAdapter();
        recyclerView.setAdapter(CardAdapter);
        cardword=new SaveWord(this);
        CardAdapter.refresh(cardword.LoadFromSavedDB());
    }

    private void setStatusBarLightMode() {
        if (this.getWindow() != null) {
            Class clazz = this.getWindow().getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(this.getWindow(), darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        ListAdapter.refresh(ListWord.LoadFromSavedDB());
        super.onDestroy();
    }
}