package com.example.mydemo1.bean;

import java.util.List;

public class CollectionDataBean {

    /**
     * curPage : 1
     * datas : [{"author":"CarGuo","chapterId":402,"chapterName":"跨平台应用","courseId":13,"desc":"不同于 GSYGithubAppFlutter 完整项目，本项目将逐步完善各种 Flutter 独立例子，方便新手学习上手和小问题方案解决。 目前开始逐步补全完善，主要提供一些有用或者有趣的例子，如果你也有好例子，环境提交 PR 。","envelopePic":"https://www.wanandroid.com/blogimgs/8aaf777c-a865-4359-84d3-79d6e27f2d60.png","id":75698,"link":"http://www.wanandroid.com/blog/show/2615","niceDate":"1天前","origin":"","originId":8655,"publishTime":1564750548000,"title":"GSY系列 Flutter 独立学习项目","userId":3,"visible":0,"zan":0},{"author":"simplezhli","chapterId":402,"chapterName":"跨平台应用","courseId":13,"desc":" Flutter 练习项目。包括完整UI设计图，更贴近真实项目的练习。","envelopePic":"https://wanandroid.com/blogimgs/2c0ccd42-50b3-4d61-a019-c2dd4af368f0.png","id":75697,"link":"http://www.wanandroid.com/blog/show/2619","niceDate":"1天前","origin":"","originId":8660,"publishTime":1564750547000,"title":" Flutter 实战项目","userId":3,"visible":0,"zan":0},{"author":"hurshi","chapterId":402,"chapterName":"跨平台应用","courseId":13,"desc":"Dio-http-cache 是 Flutter 的 http 缓存库，为 Dio 设计，就像 Android 中的 RxCache 一样；使用 sqflite 作为磁盘缓存，使用 Google/quiver-dart 的LRU算法 作为内存缓存策略；","envelopePic":"https://wanandroid.com/resources/image/pc/default_project_img.jpg","id":75696,"link":"http://www.wanandroid.com/blog/show/2622","niceDate":"1天前","origin":"","originId":8715,"publishTime":1564750544000,"title":"Flutter Dio 网络缓存库来啦  dio-http-cache","userId":3,"visible":0,"zan":0},{"author":"鸿洋","chapterId":361,"chapterName":"课程推荐","courseId":13,"desc":"","envelopePic":"","id":73456,"link":"https://market.geekbang.org/activity/channelcoupon/15?utm_source=web&amp;utm_medium=wananzhuo&amp;utm_campaign=changweiliuliang&amp;utm_term=zhanghongyang003&amp;utm_content=0530","niceDate":"2019-07-23","origin":"","originId":8537,"publishTime":1563869513000,"title":"跟极客时间申请了一波199优惠券免费送 每人仅能领取一次","userId":3,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 4
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * author : CarGuo
         * chapterId : 402
         * chapterName : 跨平台应用
         * courseId : 13
         * desc : 不同于 GSYGithubAppFlutter 完整项目，本项目将逐步完善各种 Flutter 独立例子，方便新手学习上手和小问题方案解决。 目前开始逐步补全完善，主要提供一些有用或者有趣的例子，如果你也有好例子，环境提交 PR 。
         * envelopePic : https://www.wanandroid.com/blogimgs/8aaf777c-a865-4359-84d3-79d6e27f2d60.png
         * id : 75698
         * link : http://www.wanandroid.com/blog/show/2615
         * niceDate : 1天前
         * origin :
         * originId : 8655
         * publishTime : 1564750548000
         * title : GSY系列 Flutter 独立学习项目
         * userId : 3
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
