package ${packageName};

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import ${readAccessQualifiedName};
import ${writeAccessQualifiedName};
import ${serviceQualifiedName};
import ${jsonResultQualifiedName};
import ${modelPOQualifiedName};
import ${modelVOQualifiedName};
import ${modelPOPersisterQualifiedName};
import ${beanUtilQualifiedName};

import java.util.List;


@RestController
@RequestMapping("")
public class ${modelName}ManageController {

    @Autowired
    private ${modelName}POPersister ${modelVariableName}POPersister;

    @Autowired
    private ${modelName}ManageService ${modelVariableName}ManageService;

    @PostMapping("/add")
    @WriteAccess(description = "")
    public JsonResult add(@Validated ${modelName}VO ${modelVariableName}VO){
        int res = ${modelVariableName}POPersister.baseSave(BeanUtil.convert(${modelVariableName}VO, ${modelName}PO.class));
        return JsonResult.of(res > 0, "添加成功", "添加失败");
    }

    @PostMapping("/update")
    @WriteAccess(description = "")
    public JsonResult update(@Validated ${modelName}VO ${modelVariableName}VO){
        int res = ${modelVariableName}POPersister.baseUpdate(BeanUtil.convert(${modelVariableName}VO, ${modelName}PO.class));
        return JsonResult.of(res > 0, "更新成功", "更新失败");
    }

    @PostMapping("/delete")
    @WriteAccess(description = "")
    public JsonResult deleteById(@NotNull(message = "id不能为空") String id){
        int res = ${modelVariableName}POPersister.baseDeleteById(id);
        return JsonResult.of(res > 0, "删除成功", "删除失败");
    }

    @PostMapping("/deleteBatch")
    @WriteAccess(description = "")
    public JsonResult deleteByIds(@NotNull(message = "id不能为空") String ids){
        int res = ${modelVariableName}POPersister.baseDeleteByIds(ids);
        return  JsonResult.of(res > 0, "删除成功", "删除失败");
    }

    @GetMapping("/page")
    @ReadAccess(description = "")
    public JsonResult page(@NotNull(message = "页码不能为空")Integer page, @NotNull(message = "页面大小不能为空") Integer pageSize, ${modelName}PO params){
        PageHelper.startPage(page, pageSize);
        List<${modelName}PO> ${modelVariableName}POList = ${modelVariableName}POPersister.baseFindListByParams(params);
        List<${modelName}VO> ${modelVariableName}VOList = BeanUtil.convert(${modelVariableName}POList, ${modelName}VO.class);
        return JsonResult.success("获取分页数据成功" , ${modelVariableName}VOList, ((Page)${modelVariableName}POList).getTotal());
    }

    @GetMapping("/toolBar")
    @ReadAccess(description = "")
    public JsonResult getToolBar(){
        return JsonResult.success("获取工具栏按钮成功", ${modelVariableName}ManageService.getToolbarButtons());
    }

    @GetMapping("/operatingButtons")
    @ReadAccess(description = "")
    public JsonResult getOperatingButtons(){
        return JsonResult.success("获取操作栏按钮成功", ${modelVariableName}ManageService.getOperatingButtons());
    }

}
