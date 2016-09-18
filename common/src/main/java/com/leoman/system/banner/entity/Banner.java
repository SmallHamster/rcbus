package com.leoman.system.banner.entity;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;
import com.leoman.utils.ConfigUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * Banner
 * Created by 史龙 on 2016/9/13.
 */
@Entity
@Table(name = "t_banner")
public class Banner extends BaseEntity {

    //位置 0:通勤巴士 1:永旺专线 2:用车预定
    @Column(name = "position")
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;//图片

    //是否有外链 0:无 1:有
    @Column(name = "is_oc")
    private Integer isOc;

    //外链
    @Column(name = "outside_chain")
    private String outsideChain;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getIsOc() {
        return isOc;
    }

    public void setIsOc(Integer isOc) {
        this.isOc = isOc;
    }

    public String getOutsideChain() {
        return outsideChain;
    }

    public void setOutsideChain(String outsideChain) {
        this.outsideChain = outsideChain;
    }
}
