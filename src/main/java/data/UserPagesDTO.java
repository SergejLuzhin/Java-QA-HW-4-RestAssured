package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPagesDTO {
    private Integer page;

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("total")
    private Integer totalUsers;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private ArrayList<UserDTO> data;

    public UserPagesDTO(Integer page, Integer perPage, Integer totalUsers, Integer totalPages, ArrayList<UserDTO> data) {
        this.page = page;
        this.perPage = perPage;
        this.totalUsers = totalUsers;
        this.totalPages = totalPages;
        this.data = data;
    }

    public UserPagesDTO() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<UserDTO> getData() {
        return data;
    }

    public void setData(ArrayList<UserDTO> data) {
        this.data = data;
    }
}
