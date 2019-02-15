package ${packageName};

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import ${jsonResultQualifiedName};
import ${modelPOQualifiedName};
import ${modelVOQualifiedName};
import ${modelPOHandlerQualifiedName};
import ${beanUtilQualifiedName};

import java.util.List;


@RestController
@RequestMapping("")
public class ${modelName}Controller {

    @Autowired
    private ${modelName}POHandler ${modelVariableName}POHandler;

    @PostMapping("/add")
    public JsonResult add(@Validated ${modelName}VO ${modelVariableName}VO){
        int res = ${modelVariableName}POHandler.baseSave(BeanUtils.convert(${modelVariableName}VO, ${modelName}PO.class));
        return JsonResult.of(res > 0, "添加成功", "添加失败");
    }

    @PostMapping("/update")
    public JsonResult update(@Validated ${modelName}VO ${modelVariableName}VO){
        int res = ${modelVariableName}POHandler.baseUpdate(BeanUtils.convert(${modelVariableName}VO, ${modelName}PO.class));
        return JsonResult.of(res > 0, "更新成功", "更新失败");
    }

    @PostMapping("/delete")
    public JsonResult deleteById(@NotNull(message = "id不能为空") String id){
        int res = ${modelVariableName}POHandler.baseDeleteById(id);
        return JsonResult.of(res > 0, "删除成功", "删除失败");
    }

    @PostMapping("/deleteBatch")
    public JsonResult deleteByIds(@NotNull(message = "ids不能为空") String ids){
        int res = ${modelVariableName}POHandler.baseDeleteByIds(ids);
        return  JsonResult.of(res > 0, "删除成功", "删除失败");
    }

    @GetMapping("/page")
    public JsonResult page(@NotNull(message = "页码不能为空")Integer page, @NotNull(message = "页面大小不能为空") Integer pageSize, ${modelName}PO params){
        PageHelper.startPage(page, pageSize);
        List<${modelName}PO> ${modelVariableName}POList = ${modelVariableName}POHandler.baseFindListByParams(params);
        List<${modelName}VO> ${modelVariableName}VOList = BeanUtils.convert(${modelVariableName}POList, ${modelName}VO.class);
        return JsonResult.success("获取分页数据成功" , ${modelVariableName}VOList, ((Page)${modelVariableName}POList).getTotal());
    }

}
