package com.rasyidf.kontakku.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.microsoft.fluentui.bottomsheet.BottomSheet;
import com.microsoft.fluentui.bottomsheet.BottomSheetItem;
import com.microsoft.fluentui.persona.AvatarView;
import com.rasyidf.kontakku.R;
import com.rasyidf.kontakku.TemanDetailActivity;
import com.rasyidf.kontakku.database.DBHandler;
import com.rasyidf.kontakku.database.Teman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class CustomAdapter
        extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
        implements Filterable {

  private final List<Teman> contactList;
  private final FragmentManager FM;
  private final Context context;
  private List<Teman> contactListFiltered;
  private final ContactsAdapterListener listener;
  private DBHandler DB;

  public class MyViewHolder extends RecyclerView.ViewHolder {

    private Teman selectedTeman;
    private View currentView;
    public TextView name, phone;
    public AvatarView thumbnail;
    public DBHandler DB;

    public MyViewHolder(View view, DBHandler db) {
      this(view);
      this.DB = db;
    }

    public MyViewHolder(View view) {
      super(view);
      name = view.findViewById(R.id.name);
      phone = view.findViewById(R.id.phone);
      thumbnail = view.findViewById(R.id.tbNama);

      this.currentView = view;


      thumbnail.setOnClickListener(
              v -> {
                ViewDetails(view);
              }
      );

      BottomSheetItem[] sheets = new BottomSheetItem[]{
              new BottomSheetItem(1, R.drawable.ic_info_24_regular, "Details"),
              new BottomSheetItem(2, R.drawable.ic_edit_24_filled, "Sunting", "", true),
              new BottomSheetItem(3, R.drawable.ic_delete_24_regular, "Hapus"),
      };

      ArrayList<BottomSheetItem> list = new ArrayList<>();
      Collections.addAll(list, sheets);




      view.setOnClickListener(
              v -> {
                Teman kontakItem = contactListFiltered.get(getAdapterPosition());
                listener.onTemanSelected(kontakItem);
                this.selectedTeman = kontakItem;
                BottomSheet bottomSheet = BottomSheet.newInstance(
                        list,
                        new BottomSheetItem(4, R.drawable.ic_contact, selectedTeman.getName(), selectedTeman.getPhone() )
                );
                bottomSheet.show(FM, null);

              }
      );
    }

    private void ViewDetails(View view) {
      Teman kontakItem = contactListFiltered.get(getAdapterPosition());
      Intent i = new Intent(context, TemanDetailActivity.class);
      Bundle b = new Bundle();
      b.putString("nama", kontakItem.getName());
      b.putString("nomor", kontakItem.getPhone());
      b.putBoolean("edit", false);
      i.putExtras(b);
      startActivity(context, i, b);
    }

  }

  public CustomAdapter(
          Context context,
          List<Teman> contactList,
          ContactsAdapterListener listener,
          DBHandler db,
          FragmentManager fm) {
    this.context = context;
    this.listener = listener;
    this.contactList = contactList;
    this.contactListFiltered = contactList;
    this.DB = db;
    this.FM = fm;
  }

  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.teman_item, parent, false);

    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, final int position) {
    final Teman contact = contactListFiltered.get(position);
    holder.name.setText(contact.getName());
    holder.phone.setText(contact.getPhone());
    holder.thumbnail.setName(contact.getName());
  }

  @Override
  public int getItemCount() {
    return contactListFiltered.size();
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
          contactListFiltered = contactList;
        } else {
          List<Teman> filteredList = new ArrayList<>();
          for (Teman row : contactList) {
            if (
                    row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                            row.getPhone().contains(charSequence)
            ) {
              filteredList.add(row);
            }
          }

          contactListFiltered = filteredList;
        }

        FilterResults filterResults = new FilterResults();
        filterResults.values = contactListFiltered;
        return filterResults;
      }

      @Override
      protected void publishResults(
              CharSequence charSequence,
              FilterResults filterResults
      ) {
        contactListFiltered = (List<Teman>) filterResults.values;
        notifyDataSetChanged();
      }
    };
  }

  public interface ContactsAdapterListener {
    void onTemanSelected(Teman contact);
  }
}
