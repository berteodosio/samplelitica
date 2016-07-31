package com.berteodosio.samplelitica.congressman;

import android.support.annotation.NonNull;

import com.berteodosio.samplelitica.base.BaseMVP;
import com.berteodosio.samplelitica.congressman.model.Congressman;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

public interface CongressmanMVP extends BaseMVP {

    interface Model extends BaseMVP.Model {

    }

    interface View extends BaseMVP.View {

        void displayNewCongressmanList(@NonNull final List<Congressman> congressmanList);

        void displayEmptyCongressmanList();

        void addCongressman(@NonNull final Congressman congressman);

        void displayWaitUntilRemoteCongressmanIsLoadedMessage();

        void emptyCongressman();

    }

    interface Presenter extends BaseMVP.Presenter {

        void loadLocalCongressmen();

        void loadRemoteCongressmen();

    }

    interface BO {

        Observable<List<Congressman>> loadLocalCongressmen();

        Observable<List<Congressman>> loadRemoteCongressmen();

        void persistCongressman(@NonNull final Congressman congressman);

    }

    interface DAO {

        Observable<List<Congressman>> loadCongressmen();

        void saveCongressman(@NotNull final Congressman congressman);
    }

}
