package com.rasyidf.kontakku;

import static androidx.appcompat.widget.ThemeUtils.getThemeAttrColor;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.textfield.TextInputLayout;
import com.microsoft.fluentui.persona.AvatarView;
import com.microsoft.fluentui.toolbar.Toolbar;

public class TemanDetailActivity extends AppCompatActivity {

  Toolbar tb;
  TextInputLayout tNama, tNomor;
  public AvatarView thumbnail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_detail);

    Bundle bundle = getIntent().getExtras();

    tNama = findViewById(R.id.tvNama);
    tNomor = findViewById(R.id.tvNomor);
    thumbnail = findViewById(R.id.tbNama);

    String s = bundle.getString("nama");
    tNama.getEditText().setText(s);
    tNomor.getEditText().setText(bundle.getString("nomor", "-"));
    thumbnail.setName(s);

    boolean editable = bundle.getBoolean("edit", false);
    tNama.setEnabled(editable);
    tNomor.setEnabled(editable);

    InitToolbar();
  }

  private void InitToolbar() {
    tb = findViewById(R.id.toolbar);
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
