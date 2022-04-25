package ma.emsi.gestionmachine.ui.machine;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import ma.emsi.gestionmachine.beans.Machine;
import ma.emsi.gestionmachine.databinding.FragmentBlankBinding;
import ma.emsi.gestionmachine.databinding.FragmentItemBinding;
import ma.emsi.gestionmachine.ui.machine.placeholder.PlaceholderContent;


/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderContent.PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> implements Filterable {
    private  List<Machine> machinesAll;
    private final List<Machine> machines;

    public MyItemRecyclerViewAdapter(List<Machine> items) {
        machines = items;
        machinesAll = new ArrayList<>(machines);
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Machine> filteredList = new ArrayList<Machine>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(machinesAll);
            }
            else {
                for(Machine machine : machinesAll){
                    if(machine.getMarque().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))){
                        filteredList.add(machine);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            machines.clear();
            machines.addAll((Collection<? extends Machine>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.machine = machines.get(position);
        holder.ref.setText(machines.get(position).getReference());
        holder.dateAchat.setText(machines.get(position).getDateAchat()+"");
        holder.marque.setText(machines.get(position).getMarque());
        holder.prix.setText(machines.get(position).getPrix()+"");
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView idM;
        public final TextView ref;
        public final TextView dateAchat;
        public final TextView prix;
        public final TextView marque;
        public Machine machine;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            idM = binding.idM;
            ref = binding.referenceu;
            dateAchat = binding.dateu;
            prix = binding.prixu;
            marque = binding.marqueu;

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}