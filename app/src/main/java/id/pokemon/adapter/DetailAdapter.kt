package id.pokemon.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonrysimbolon.base.adapter.BaseRecyclerViewAdapter
import com.pokemon.data.model.AbilityModel
import id.pokemon.databinding.PokemonAdapterBinding

private val random = java.util.Random()

class DetailPokemonViewHolder(
    private val binding: PokemonAdapterBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(item: AbilityModel) {
        val colorPair = getRandomColorPair()

        binding.apply {
            cardPokemon.setCardBackgroundColor(colorPair.first)
            itemText.text = item.name
            itemText.setTextColor(colorPair.second)
        }
    }

    private fun getRandomColorPair(): Pair<Int, Int> {
        val slowColor = Color.rgb(
            100 + random.nextInt(100),
            100 + random.nextInt(100),
            100 + random.nextInt(100)
        )
        val darkColor = Color.rgb(
            random.nextInt(50),
            random.nextInt(50),
            random.nextInt(50)
        )
        return Pair(slowColor, darkColor)
    }
}

class DetailAdapter : BaseRecyclerViewAdapter<DetailPokemonViewHolder, Int, AbilityModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPokemonViewHolder =
        DetailPokemonViewHolder(
            PokemonAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DetailPokemonViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener { view ->
            onClickItem?.let { it(view, item) }
        }
    }

}