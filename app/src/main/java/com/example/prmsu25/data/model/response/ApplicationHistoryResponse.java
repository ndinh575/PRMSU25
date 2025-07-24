package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.Application;

import java.util.List;

public class ApplicationHistoryResponse {
    private boolean success;
    private String message;
    private List<Application> data;
    private Pagination pagination;

    public ApplicationHistoryResponse() {
    }

    public ApplicationHistoryResponse(boolean success, String message, List<Application> data, Pagination pagination) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<Application> getData() { return data; }
    public void setData(List<Application> data) { this.data = data; }

    public Pagination getPagination() { return pagination; }
    public void setPagination(Pagination pagination) { this.pagination = pagination; }

    public static class Pagination{
        private int currentPage;
        private int totalPages;
        private int totalApplications;
        private boolean hasNextPage;
        private boolean hasPrevPage;

        public int getCurrentPage() { return currentPage; }
        public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

        public int getTotalApplications() { return totalApplications; }
        public void setTotalApplications(int totalApplications) { this.totalApplications = totalApplications; }

        public boolean isHasNextPage() { return hasNextPage; }
        public void setHasNextPage(boolean hasNextPage) { this.hasNextPage = hasNextPage; }

        public boolean isHasPrevPage() { return hasPrevPage; }
        public void setHasPrevPage(boolean hasPrevPage) { this.hasPrevPage = hasPrevPage; }
    }
}

