package com.jida.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.jida.db.greendao.DaoSession;
import com.jida.db.greendao.TestUserDao;
import com.jida.db.greendao.TestTeacherDao;

/**
 * Created by chenyuye on 17/11/30.
 */
@Entity(active = true)
public class TestTeacher {

    @Id(autoincrement = true)
    private Long ID;

    private Boolean sex;

    private Integer age;

    private String name;

    private Long uid;
    @ToOne(joinProperty="uid")
    private TestUser user;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 829639500)
    private transient TestTeacherDao myDao;
    @Generated(hash = 26752603)
    public TestTeacher(Long ID, Boolean sex, Integer age, String name, Long uid) {
        this.ID = ID;
        this.sex = sex;
        this.age = age;
        this.name = name;
        this.uid = uid;
    }
    @Generated(hash = 561837900)
    public TestTeacher() {
    }

    @Override
    public String toString() {
        return "Teacher: ID="+ ID+
                " sex="+ sex+
                " age="+ age+
                " name="+ name+
                " uid="+ getUser().toString();
    }

    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public Boolean getSex() {
        return this.sex;
    }
    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return this.age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getUid() {
        return this.uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1371510638)
    public TestUser getUser() {
        Long __key = this.uid;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestUserDao targetDao = daoSession.getTestUserDao();
            TestUser userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2120509203)
    public void setUser(TestUser user) {
        synchronized (this) {
            this.user = user;
            uid = user == null ? null : user.getID();
            user__resolvedKey = uid;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 52362418)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestTeacherDao() : null;
    }

}
