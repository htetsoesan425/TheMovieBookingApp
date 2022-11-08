import alirezat775.lib.carouselview.CarouselAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.data.vos.CardVO
import com.padc.kotlin.ftc.themoviebooking.models.SampleModel
import kotlinx.android.synthetic.main.view_holder_carousel.view.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-06-14
 * Email:   alirezat775@gmail.com
 */

class CardCarouselAdapter : CarouselAdapter() {

    private var mCards: List<CardVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_carousel, parent, false)
        return CardCarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        if (mCards.isNotEmpty()) {
            holder.bindData(mCards[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cards: List<CardVO>) {
        mCards = cards
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mCards.count()

    inner class CardCarouselViewHolder(itemView: View) : CarouselViewHolder(itemView)

    interface OnClick {
        fun click(model: SampleModel)
    }
}

private fun CarouselAdapter.CarouselViewHolder.bindData(cardVO: CardVO) {
    itemView.tv4stars1.text = cardVO.cardNumber
    itemView.tvCardHolderName.text = cardVO.cardHolder
    itemView.tvExpireDate.text = cardVO.expirationDate
}
