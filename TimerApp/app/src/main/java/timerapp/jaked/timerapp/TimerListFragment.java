package timerapp.jaked.timerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import timerapp.jaked.timerapp.database.TimerDbSchema;

public class TimerListFragment extends Fragment {

    OnTimerSelectedListener mCallback;

    private RecyclerView mTimerRecyclerView;
    private TimerAdapter mAdapter;
    private MyTimer mMyTimer;
    private Button mNewTimer;

    private static final String TIME_PICKER="TimePicker";

    private static final int REQUEST_TIMER = 0;

    public interface OnTimerSelectedListener {
        public void onTimerSelected(UUID uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_list, container, false);

        mTimerRecyclerView = (RecyclerView) view.findViewById(R.id.timer_recycler_view);

        mTimerRecyclerView.setLayoutManager(new  LinearLayoutManager(getActivity()));


        mNewTimer = (Button) view.findViewById(R.id.btNewTimer);
        mNewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                TimerCreatorDialogFragment dialog = new TimerCreatorDialogFragment();

                dialog.setTargetFragment(TimerListFragment.this,REQUEST_TIMER);
                dialog.show(manager,TIME_PICKER);

/*                CreateTimerFragment createTimerFragment = new CreateTimerFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_bottom, createTimerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        updateList();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_TIMER){
            int timerMinutes = (int) data.getSerializableExtra(TimerCreatorDialogFragment.EXTRA_CREATE_TIMER_MINUTES);
            int timerSeconds = (int) data.getSerializableExtra(TimerCreatorDialogFragment.EXTRA_CREATE_TIMER_SECONDS);
            MyTimer mytimer = new MyTimer();
            mytimer.setTimerMinutes(timerMinutes);
            mytimer.setTimerSeconds(timerSeconds);
            mytimer.setTimerTitle(timerMinutes,timerSeconds);
            TimerLab.get(getActivity()).addTimer(mytimer);
            updateList();
        }
    }

    private class TimerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private TextView mtvTimer;
        private Button btRemove;
        private MyTimer mMyTimer;

        public void bind (MyTimer myTimer){
            mMyTimer = myTimer;
            mtvTimer.setText(myTimer.getTimerTitle());
        }

        public TimerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_timer,parent,false    ));
            itemView.setOnClickListener(this);

            mtvTimer = (TextView) itemView.findViewById(R.id.tvTimer);
            btRemove = (Button) itemView.findViewById(R.id.btRemove);

            btRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimerLab.get(getActivity()).removeTimer(mMyTimer);
                    updateList();
                }
            });
        }

        @Override
        public void onClick(View view) {
            mCallback = (OnTimerSelectedListener) getActivity();
            System.out.println(mMyTimer.getId());
            mCallback.onTimerSelected(mMyTimer.getId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private class TimerAdapter extends RecyclerView.Adapter<TimerHolder>{
        private List<MyTimer> mMyTimers;

        public TimerAdapter(List<MyTimer> myTimers){ mMyTimers = myTimers;}

        @Override
        public TimerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TimerHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(TimerHolder holder, int position) {
            MyTimer myTimer = mMyTimers.get(position);
            holder.bind(myTimer);
        }

        @Override
        public int getItemCount() {
            return mMyTimers.size();
        }

        public void setMyTimers(List<MyTimer> myTimers){
            mMyTimers = myTimers;
        }
    }

    private void updateList(){
        TimerLab timerLab = TimerLab.get(getActivity());
        List<MyTimer> myTimers = timerLab.getMyTimers();

        if(mAdapter == null){
            mAdapter = new TimerAdapter(myTimers);
            mTimerRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setMyTimers(myTimers);
            mAdapter.notifyDataSetChanged();
        }
    }


}
