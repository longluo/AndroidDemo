package com.longluo.demo.video;

public class ThumbVideoKey {
    public long video_id;
    public boolean isMiniKind = true;

    public ThumbVideoKey(long id, boolean isMini) {
        video_id = id;
        isMiniKind = isMini;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ThumbVideoKey) && (video_id == ((ThumbVideoKey) o).video_id)
                && (isMiniKind == ((ThumbVideoKey) o).isMiniKind);
    }

    @Override
    public int hashCode() {

        int result = 17;
        int c = (int) (video_id ^ (video_id >>> 32));
        result = 37 * result + c;
        c = isMiniKind ? 1 : 0;
        result = 37 * result + c;

        return result;
    }
}
