package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.Job;

import java.util.List;

public class JobResponse {
    private boolean success;
    private String message;
    private List<Job> data;
    private Pagination pagination;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Job> getData() { return data; }
    public Pagination getPagination() { return pagination; }

    public static class Pagination {
        private int currentPage;
        private int totalPages;
        private int totalJobs;
        private boolean hasNextPage;
        private boolean hasPrevPage;

        public int getCurrentPage() { return currentPage; }
        public int getTotalPages() { return totalPages; }
        public int getTotalJobs() { return totalJobs; }
        public boolean isHasNextPage() { return hasNextPage; }
        public boolean isHasPrevPage() { return hasPrevPage; }
    }
}
