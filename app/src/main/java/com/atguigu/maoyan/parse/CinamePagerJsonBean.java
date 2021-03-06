package com.atguigu.maoyan.parse;

import java.util.List;

/**
 * Created by hp on 2016/6/25.
 */
public class CinamePagerJsonBean {
    public List<Areas> areas;

    public List<Areas> getAreas() {
        return areas;
    }

    public void setAreas(List<Areas> areas) {
        this.areas = areas;
    }

    public class Areas {
        public List<AreasAddress> areasAddresses;

        public List<AreasAddress> getAreasAddresses() {
            return areasAddresses;
        }

        public void setAreasAddresses(List<AreasAddress> areasAddresses) {
            this.areasAddresses = areasAddresses;
        }

        public class AreasAddress {
            public String addr;
            public String area;
            public String brd;
            public int brdId;
            public String ct;
            public int deal;
            public int dealPrice;
            public String dis;
            public int distance;
            public int follow;
            public int id;
            public int imax;
            public double lat;
            public double lng;
            public String nm;
            public int poiId;
            public int preferential;
            public int referencePrice;
            public boolean sell;
            public double sellPrice;
            public int sellmin;
            public int showCount;
        }


    }


}
