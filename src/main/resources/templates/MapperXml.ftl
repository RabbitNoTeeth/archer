<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperQualifiedName}">

    <!-- 实体映射集 -->
    <resultMap id="${modelVariableName}Map" type="${modelQualifiedName}">
        <id property="id" column="id"/>
        <result property="createTime" column="create_time"/>
        <result property="createBy" column="create_by"/>
        <result property="updateTime" column="update_time"/>
        <result property="updateBy" column="update_by"/>
        <result property="remarks" column="remarks"/>
        <result property="sort" column="sort"/>

        ${resultItems}
    </resultMap>

    <!-- 插入 -->
    <insert id="baseSave" parameterType="${modelQualifiedName}">
        insert into t_tableName
        (id, create_time, create_by, update_time, update_by, remarks, sort,
        ${baseSaveColumns} )
        values
        (#{id}, #{createTime}, #{createBy}, #{updateTime}, #{updateBy}, #{remarks}, #{sort},
        ${baseSaveValues})
    </insert>

    <!-- 更新 -->
    <update id="baseUpdate" parameterType="${modelQualifiedName}">
        update t_tableName
        <set>
            update_time = #{updateTime},
            update_by = #{updateBy},
            ${baseUpdateItems}
            <if test="remarks != null">
                remarks = #{remarks},
            </if>
            <if test="sort != null">
                sort = #{sort},
            </if>
        </set>
        where id = #{id}
    </update>

    <!-- 删除 -->
    <delete id="baseDeleteById" parameterType="string">
        delete from t_tableName
        where id = #{id}
    </delete>

    <!-- 批量删除 -->
    <delete id="baseDeleteByIds">
        delete from t_tableName
        where id in
        <foreach collection="ids" index="index" item="item" open="(" close=")" separator=",">
        #{item}
        </foreach>
    </delete>

    <!-- 查询单个 -->
    <select id="baseFindById" parameterType="string" resultMap="${modelVariableName}Map">
        select * from t_tableName
        where id = #{id}
    </select>

    <!-- 查询所有 -->
    <select id="baseFindAll" resultMap="${modelVariableName}Map">
        select * from t_tableName
    </select>

    <!-- 条件查询列表 -->
    <select id="baseFindListByParams" parameterType="${modelQualifiedName}" resultMap="${modelVariableName}Map">
        select * from t_tableName
        <where>
            1 = 1
        </where>
        order by sort asc , create_time desc
    </select>

    <!-- 条件查询列表 -->
    <select id="baseFindListByFilters" resultMap="${modelVariableName}Map">
        select * from t_tableName
        <where>
            1 = 1
        </where>
        order by sort asc , create_time desc
    </select>

</mapper>