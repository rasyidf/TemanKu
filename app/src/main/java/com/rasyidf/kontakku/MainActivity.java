package com.rasyidf.kontakku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.microsoft.fluentui.appbarlayout.AppBarLayout;
import com.microsoft.fluentui.bottomsheet.BottomSheet;
import com.microsoft.fluentui.bottomsheet.BottomSheetItem;
import com.microsoft.fluentui.search.Searchbar;
import com.microsoft.fluentui.snackbar.Snackbar;
import com.rasyidf.kontakku.adapter.CustomAdapter;
import com.rasyidf.kontakku.database.DBHandler;
import com.rasyidf.kontakku.database.Teman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity
        extends AppCompatActivity
        implements
        SearchView.OnQueryTextListener, CustomAdapter.ContactsAdapterListener, BottomSheetItem.OnClickListener {

  protected RecyclerView.LayoutManager mLayoutManager;
  protected RecyclerView mRecyclerView;
  protected CustomAdapter mAdapter;
  protected DBHandler dbHandler;
  Searchbar searchbar;
  AppBarLayout app_bar;
  List<Teman> listNama;
  private Teman selectedTeman;
  private ArrayList<BottomSheetItem> bottomSheetList;
  private ArrayList<BottomSheetItem> bottomSheetConfirm;
  private int selectedIndex;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.Theme_KontakKu);
    setContentView(R.layout.activity_main);
    initDB();
    initStatusbar();

    InitBottomSheetItems();

    enumerateList();
    initList();
  }

  private void InitBottomSheetItems() {
    BottomSheetItem[] sheets = new BottomSheetItem[]{
            new BottomSheetItem(1, R.drawable.ic_info_24_regular, "Details"),
            new BottomSheetItem(2, R.drawable.ic_edit_24_filled, "Sunting", "", true),
            new BottomSheetItem(3, R.drawable.ic_delete_24_regular, "Hapus"),
    };
    BottomSheetItem[] sheets2 = new BottomSheetItem[]{
            new BottomSheetItem(41,"Ya"),
            new BottomSheetItem(42,"Tidak"), 
    };
    bottomSheetList = new ArrayList<>();
    bottomSheetConfirm = new ArrayList<>();
    Collections.addAll(bottomSheetList, sheets);
    Collections.addAll(bottomSheetConfirm, sheets2);
  }

  private void initDB() {
    this.dbHandler = new DBHandler(this);
  }

  private void enumerateList() {
//    Teman[] contacs = new Teman[]{
//            new Teman(1, "Ani", "085123456789"),
//            new Teman(2, "Budi", "085123456789"),
//            new Teman(3, "Caca", "085123456789"),
//            new Teman(4, "Danu", "085123456789"),
//            new Teman(5, "Ervan", "085123456789"),
//            new Teman(6, "Fatimah", "085123456789"),
//            new Teman(7, "Inayah", "085123456789"),
//            new Teman(8, "Ilham", "085123456789"),
//            new Teman(9, "Eris", "085123456789"),
//            new Teman(10, "Gita", "085123456789"),
//            new Teman(11, "Maul", "085123456789"),
//            new Teman(12, "Fikri", "085123456789"),
//            new Teman(13, "Vian", "085123456789"),
//            new Teman(14, "Lutfi", "085123456789"),
//            new Teman(15, "Sobari", "085123456789"),
//            new Teman(16, "Hakim", "085123456789"),
//            new Teman(17, "Tyas", "085123456789"),
//            new Teman(18, "Rasyid", "085123456789"),
//    };
//    listNama = new ArrayList<>();
//    for (Teman ki : contacs) {
//      this.listNama.add(ki);
//      this.dbHandler.insert(ki);
//    }
    this.listNama = this.dbHandler.getAll();
  }

  private void initList() {
    mRecyclerView = findViewById(R.id.lstKontak);

    mLayoutManager = new LinearLayoutManager(getApplicationContext());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mAdapter =
            new CustomAdapter(
                    getApplicationContext(),
                    listNama,
                    this
            );
    mRecyclerView.setAdapter(mAdapter);

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
            mRecyclerView.getContext(),
            DividerItemDecoration.HORIZONTAL
    );
    dividerItemDecoration.setDrawable(
            Objects.requireNonNull(ContextCompat.getDrawable(MainActivity.this, R.drawable.ms_row_divider))
    );
    mRecyclerView.addItemDecoration(dividerItemDecoration);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }



  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.mnuSettings:
        Toast
                .makeText(
                        getApplicationContext(),
                        R.string.app_name,
                        Toast.LENGTH_LONG
                )
                .show();
        break;
      case R.id.mnuAbout:
        AlertDialog.Builder adbs = new AlertDialog.Builder(this);
        adbs.setTitle(getResources().getString(R.string.app_name));
        adbs.setIcon(R.drawable.logo);
        adbs.setMessage(R.string.copyright);
        adbs.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                  }
                }
        );
        AlertDialog ad = adbs.create();
        ad.show();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initStatusbar() {

    Drawable searchIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ms_ic_search_24_filled);
    app_bar = findViewById(R.id.app_bar);
    app_bar.setScrollBehavior(AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR);
    searchbar = new Searchbar(this);
    searchbar.setOnQueryTextListener(this);
    searchbar.setActionMenuView(false);
    searchbar.setLeft(16);
    app_bar.getToolbar().setTitle(getString(R.string.app_name));
    app_bar.setAccessoryView(searchbar);
    findViewById(R.id.fab).setOnClickListener(v -> {
      Intent i = new Intent(
              getApplicationContext(),
              TemanDetailActivity.class
      );
      Bundle b = new Bundle();
      b.putString("nama","");
      b.putString("nomor", "");
      b.putBoolean("new", true);
      i.putExtras(b);
      startActivityForResult(i, 1);

    });
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    mAdapter.getFilter().filter(query);
    return true;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    mAdapter.getFilter().filter(newText);
    return true;
  }

  @Override
  public void onTemanSelected(Teman contact, int pos) {
    selectedTeman = contact;
    selectedIndex = pos;
    BottomSheet bottomSheet = BottomSheet.newInstance(
            bottomSheetList,
            new BottomSheetItem(4, R.drawable.ic_contact, selectedTeman.getName(), selectedTeman.getPhone())
    );
    bottomSheet.show(getSupportFragmentManager(), null);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) { 
      mAdapter.notifyDataSetChanged();

      if (requestCode == 1) {
        Snackbar.Companion.make(getWindow().getDecorView(), "Berhasil Di Tambahkan", 2000, Snackbar.Style.ANNOUNCEMENT).show();
      }  else if (requestCode == 2) {
        Snackbar.Companion.make(getWindow().getDecorView(), "Perubahan Berhasil Disimpan", 2000, Snackbar.Style.ANNOUNCEMENT).show();
      }
    }

  }

  @Override
  public void onBottomSheetItemClick(@NotNull BottomSheetItem bottomSheetItem) {
    if (selectedTeman != null) {
      Intent i = new Intent(
              getApplicationContext(),
              TemanDetailActivity.class
      );
      Bundle b = new Bundle();
      b.putString("nama", selectedTeman.getName());
      b.putString("nomor", selectedTeman.getPhone());
      b.putInt("id", selectedTeman.getId());
      switch (bottomSheetItem.getId()) {
        case 1:
          b.putBoolean("edit", false);
          i.putExtras(b);
          startActivity(i);
          break;
        case 2:
          b.putBoolean("edit", true);
          i.putExtras(b);
          startActivityForResult(i,2);
          break;
        case 3:
          BottomSheet bottomSheet = BottomSheet.newInstance(
                  bottomSheetConfirm,
                  new BottomSheetItem(1,  "Apakah anda ingin menghapus kontak ini?")
          );
          bottomSheet.show(getSupportFragmentManager(), null);
          break;
        case 41:
            if (dbHandler == null) {
              dbHandler = new DBHandler(getApplicationContext());
            }

            dbHandler.delete(selectedTeman);
            mAdapter.notifyItemRemoved(selectedIndex); 
            Snackbar.Companion.make(getWindow().getDecorView(), "Berhasil Dihapus", 2000, Snackbar.Style.ANNOUNCEMENT).show();
          break;
        default:
          break;
      }
    }
  }

}
