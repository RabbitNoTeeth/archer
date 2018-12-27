<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperQualifiedName}">

    <!-- 实体映射集 -->
    <resultMap id="${modelVariableName}Map" type="${modelQualifiedName}">
        <id property="id" column="id"/>
        <result property="createdTime" column="created_time"/>
        <result property="createdBy" column="created_by"/>
        <result property="updatedTime" column="updated_time"/>
        <result property="updatedBy" column="updated_by"/>
        <result property="remarks" column="remarks"/>
        <result property="sort" column="sort"/>

        ${resultItems}
    </resultMap>

    <!-- 插入 -->
    <insert id="baseSave" parameterType="${modelQualifiedName}">
        INSERT INTO ${tableName}
        (id, created_time, created_by, updated_time, updated_by, remarks, sort
        ${baseSaveColumns} )
        VALUES
        (#{id}, #{createdTime}, #{createdBy}, #{updatedTime}, #{updatedBy}, #{remarks}, #{sort}
        ${baseSaveValues})
    </insert>

    <!-- 批量插入 -->
    <insert id="baseSaveBatch" parameterType="${modelQualifiedName}">
        INSERT INTO ${tableName}
        (id, created_time, created_by, updated_time, updated_by, remarks, sort
        ${baseSaveColumns})
        VALUES
        <foreach collection="beans" index="index" item="item" open="" separator="," close="">
            (#{item.id}, #{item.createdTime}, #{item.createdBy}, #{item.updatedTime}, #{item.updatedBy}, #{item.remarks}, #{item.sort}
            ${baseSaveBatchValues})
        </foreach>
    </insert>

    <!-- 更新 -->
    <update id="baseUpdate" parameterType="${modelQualifiedName}">
        UPDATE ${tableName}
        <set>
            updated_time = #{updatedTime},
            updated_by = #{updatedBy},
            ${baseUpdateItems}
            <if test="remarks != null">
                remarks = #{remarks},
            </if>
            <if test="sort != null">
                sort = #{sort},
            </if>
        </set>
        WHERE id = #{id}
    </update>

    <!-- 批量更新 -->
    <update id="baseUpdateBatch" parameterType="${modelQualifiedName}">
        UPDATE ${tableName}
        <set>
            updated_time =
            <foreach collection="beans" item="item" index="index" separator=" " open="case id" close="end">
                WHEN #{item.id} THEN #{item.updatedTime}
            </foreach>,
            updated_by =
            <foreach collection="beans" item="item" index="index" separator=" " open="case id" close="end">
                WHEN #{item.id} THEN #{item.updatedBy}
            </foreach>,
            ${baseUpdateBatchItems}
            remarks =
            <foreach collection="beans" item="item" index="index" separator=" " open="case id" close="end">
                WHEN #{item.id} THEN #{item.remarks}
            </foreach>,
            sort =
            <foreach collection="beans" item="item" index="index" separator=" " open="case id" close="end">
                WHEN #{item.id} THEN #{item.sort}
            </foreach>
        </set>
        WHERE id IN
        <foreach collection="beans" index="index" item="item" separator="," open="(" close=")">
            #{item.id}
        </foreach>
    </update>

    <!-- 根据id删除 -->
    <delete id="baseDeleteById" parameterType="string">
        DELETE FROM ${tableName}
        WHERE id = #{id}
    </delete>

    <!-- 根据id批量删除 -->
    <delete id="baseDeleteByIds">
        DELETE FROM ${tableName}
        WHERE id in
        <foreach collection="ids" index="index" item="item" open="(" close=")" separator=",">
            #{item}
        </foreach>
    </delete>

    <!-- 根据id查询单个 -->
    <select id="baseFindById" parameterType="string" resultMap="${modelVariableName}Map">
        SELECT * FROM ${tableName}
        WHERE id = #{id}
    </select>

    <!-- 根据id查询多个 -->
    <select id="baseFindListByIds">
        SELECT * FROM ${tableName}
        WHERE id IN
        <foreach collection="ids" index="index" item="item" open="(" close=")" separator=",">
            #{item}
        </foreach>
    </select>

    <!-- 条件查询列表 -->
    <select id="baseFindListByParams" parameterType="${modelQualifiedName}" resultMap="${modelVariableName}Map">
        SELECT * FROM ${tableName}
        <where>
            1 = 1
            ${baseFindListByParamsItems}
        </where>
        ORDER BY sort ASC , created_time DESC
    </select>

    <!-- 查询所有 -->
    <select id="baseFindAll" resultMap="${modelVariableName}Map">
        SELECT * FROM ${tableName}
        ORDER BY sort ASC , created_time DESC
    </select>

</mapper>