//package film.com.viwafo.example.Fragment;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
//import film.com.viwafo.example.R;
//
//
//public class MovieFragment extends BaseFragment implements MovieAdapter.TaskCallBack {
//
//    private ListView lvContact;
//    ArrayList<ItemMovie> arrayList = new ArrayList<>();
//
//    public MovieFragment() {
//    }
//
//    @Override
//    protected void mapView(View view) {
//        lvContact = (ListView) view.findViewById(R.id.lvMovie);
//
//    }
//
//    @Override
//    protected void mapData() {
//        ItemMovie itemMovie1 = new ItemMovie("Tranformer", R.mipmap.newyear, "23/6/2017", "5.3/10.0", "Con người và các Transformer đang có chiến tranh, Optimus Prime đã biến mất.");
//        ItemMovie itemMovie2 = new ItemMovie("The Mummy", R.mipmap.smile, "9/6/2017", "5.8/10.0", "Tưởng đã yên nghỉ vùi sâu trong một ngôi mộ sâu dưới sa mạc khủng khiếp.");
//        ItemMovie itemMovie3 = new ItemMovie("Wonder Woman", R.mipmap.noel, "2/6/2017", "7.0/10.0", "Bộ phim kể về quá khứ của nữ chiến binh huyền thoại Diana Prince trước khi nàng trở thành Wonder Woman gặp gỡ Người Dơi và Siêu Nhân trong Batman v Superman: Dawn Of Justice. ");
//
//        arrayList.add(itemMovie1);
//        arrayList.add(itemMovie2);
//        arrayList.add(itemMovie3);
//
//        final MovieAdapter movieAdapter = new MovieAdapter(getActivity(), this, R.layout.item_recycler_home_list, arrayList);
//
//        lvContact.post(new Runnable() {
//            public void run() {
//                lvContact.setAdapter(movieAdapter);
//            }
//        });
//    }
//
//
//    @Override
//    protected int getResIdLayout() {
//        return R.layout.movie_fragment;
//
//    }
//
//    @Override
//    public void done() {
//        getActivity().finish();
//    }
//}
//
