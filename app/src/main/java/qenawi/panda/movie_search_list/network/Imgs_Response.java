package qenawi.panda.movie_search_list.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import qenawi.panda.a_predator.network_Handeler.CService_DBase;

import java.util.List;

public class Imgs_Response extends CService_DBase
{
    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean Is_Data_Good()
    {
        return profiles!=null;
    }
}
