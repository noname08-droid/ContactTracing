package com.example.drawerapplication;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drawerapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FloatingActionButton gmail, information, fab;
    private BottomAppBar bottomAppBar;
    boolean isOpen = false;
    Float translationYaxis = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gmail = binding.appBarMain.gmail;
        information = binding.appBarMain.information;
        fab = binding.appBarMain.fab;
//        bottomAppBar = binding.appBarMain.bottomAppBar;


        setSupportActionBar(binding.appBarMain.toolbar);

        ShowMenu();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



//        float radius = getResources().getDimension(R.dimen.cardview_default_radius);
//        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
//        bottomBarBackground.setShapeAppearanceModel(
//                bottomBarBackground.getShapeAppearanceModel()
//                        .toBuilder()
//                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
//                        .setTopLeftCorner(CornerFamily.ROUNDED,radius)
//                        .build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void ShowMenu(){
        gmail.setAlpha(0f);
        information.setAlpha(0f);

        gmail.setTranslationY(translationYaxis);
        information.setTranslationY(translationYaxis);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen){
                    Closemenu();
                }else{
                    Openmenu();
                }
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmailDialog();
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void Openmenu() {
        isOpen = !isOpen;
        fab.setImageResource(R.drawable.ic_baseline_info_24);
        gmail.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        information.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        gmail.setVisibility(View.VISIBLE);
        information.setVisibility(View.VISIBLE);
    }

    private void Closemenu() {
        isOpen = !isOpen;
        fab.setImageResource(R.drawable.ic_baseline_help_24);
        gmail.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        information.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        gmail.setVisibility(View.INVISIBLE);
        information.setVisibility(View.INVISIBLE);
    }
    private void openDialog(){
        DialogActivity dialogActivity = new DialogActivity();
        dialogActivity.show(getSupportFragmentManager(),"dialogActivity");
    }
    private void gmailDialog(){
        GmailDialog gmailDialog = new GmailDialog();
        gmailDialog.show(getSupportFragmentManager(),"gmailDialog");
    }

}