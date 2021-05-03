package com.rasyidf.kontakku;

import static androidx.appcompat.widget.ThemeUtils.getThemeAttrColor;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.textfield.TextInputLayout;
import com.microsoft.fluentui.persona.AvatarView;
import com.microsoft.fluentui.snackbar.Snackbar;
import com.microsoft.fluentui.toolbar.Toolbar;
import com.microsoft.fluentui.widget.Button;
import com.rasyidf.kontakku.database.DBHandler;
import com.rasyidf.kontakku.database.Teman;

public class TemanDetailActivity extends AppCompatActivity {

  Toolbar tb;
  TextInputLayout tNama, tNomor;
  public AvatarView thumbnail;
  Button bSimpan;
  public boolean isEditable, isNew;
  private DBHandler DB;
  private int currentId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_detail);

    Bundle bundle = getIntent().getExtras();

    tNama = findViewById(R.id.tvNama);
    tNomor = findViewById(R.id.tvNomor);
    bSimpan = findViewById(R.id.bSimpan);
    thumbnail = findViewById(R.id.tbNama);
    DB = new DBHandler(this);
    if (bundle != null) {
      currentId = bundle.getInt("id");
      String s = bundle.getString("nama", "");
      tNama.getEditText().setText(s);
      tNomor.getEditText().setText(bundle.getString("nomor", "-"));
      thumbnail.setName(s);

      isEditable = bundle.getBoolean("edit", false);
      isNew = bundle.getBoolean("new", false);
      tNama.setEnabled(isEditable || isNew);
      tNomor.setEnabled(isEditable || isNew);
      bSimpan.setVisibility((isEditable||isNew)? View.VISIBLE:View.INVISIBLE);

    }
    bSimpan.setOnClickListener(v -> {
      Teman t = new Teman();
      t.setName(tNama.getEditText().getText().toString());
      t.setPhone(tNomor.getEditText().getText().toString());
      if (isNew){
        DB.insert(t);
      } else {

        t.setId(currentId);

        DB.update(t);
        Snackbar.Companion.make(getWindow().getDecorView(), "Berhasil Di Update", 2000, Snackbar.Style.ANNOUNCEMENT).show();
      }
      setResult(RESULT_OK);
      finish();
    });
    InitToolbar();
  }

  private void InitToolbar() {
    tb = findViewById(R.id.toolbar);
    if (isNew){
      tb.setTitle("Teman Baru");
    } else if (isEditable){
      tb.setTitle("Sunting Informasi Teman");
    } else {
      tb.setTitle("Detail Teman");
    }
    Drawable backArrow = ContextCompat.getDrawable(
      this,
      R.drawable.ms_ic_arrow_left_24_filled_toolbar
    );
    if (backArrow != null) {
      backArrow.setTint(getColor(R.color.fluentui_white));
      tb.setNavigationIcon(backArrow);
      tb.setNavigationOnClickListener(
        v -> {
          onBackPressed();
        }
      );
    }
  }
}
