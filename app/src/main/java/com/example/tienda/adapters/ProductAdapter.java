package com.example.tienda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.R;
import com.example.tienda.models.Product;
import com.example.tienda.services.network.ApiService;
import com.example.tienda.services.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private ApiService apiService;

    public ProductAdapter(List<Product> products) {
        this.products = products;
        this.apiService = RetrofitClient.getApiService(); // Instancia de API
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("$" + product.getPrice());

        holder.btnShowProduct.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Producto: " + product.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.btnDeleteProduct.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            apiService.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        products.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                        Toast.makeText(v.getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Error al eliminar (API): " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(v.getContext(), "Fallo en conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        Button btnShowProduct, btnDeleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnShowProduct = itemView.findViewById(R.id.btnShowProduct);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}
