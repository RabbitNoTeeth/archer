
package ${packageName};

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${readAccessQualifiedName};
import ${writeAccessQualifiedName};
import ${serviceQualifiedName};
import ${jsonResultQualifiedName};
import ${modelQualifiedName};

import java.util.List;


@RestController
@RequestMapping("")
public class ${modelName}Controller {

    @Autowired
    private ${modelName}Service ${modelVariableName}Service;

    @GetMapping("/page")
    @ReadAccess(description = "")
    public JsonResult page(Integer page, Integer pageSize, ${modelName} ${modelVariableName}){
        PageHelper.startPage(page,pageSize);
        List<${modelName}> res = ${modelVariableName}Service.baseFindListByParams(${modelVariableName});
        return new JsonResult(true, "获取分页列表数据成功", res, ((Page)res).getTotal());
    }

    @PostMapping("/add")
    @WriteAccess(description = "")
    public JsonResult add(${modelName} ${modelVariableName}){
        int res = ${modelVariableName}Service.baseSave(${modelVariableName});
        return new JsonResult(res > 0,res > 0 ? "添加成功" : "添加失败");
    }

    @PostMapping("/update")
    @WriteAccess(description = "")
    public JsonResult update(${modelName} ${modelVariableName}){
        int res = ${modelVariableName}Service.baseUpdate(${modelVariableName});
        return new JsonResult(res > 0, res > 0 ? "更新成功" : "更新失败");
    }

    @PostMapping("/delete")
    @WriteAccess(description = "")
    public JsonResult deleteById(String id){
        int res = ${modelVariableName}Service.baseDeleteById(id);
        return new JsonResult(res > 0, res > 0 ? "删除成功" : "删除失败");
    }

    @PostMapping("/deleteBatch")
    @WriteAccess(description = "")
    public JsonResult deleteByIds(String ids){
        int res = ${modelVariableName}Service.baseDeleteByIds(ids);
        return new JsonResult(res > 0, res > 0 ? "删除成功" : "删除失败");
    }

    @GetMapping("/getToolBarButtons")
    @ReadAccess(description = "")
    public JsonResult getToolBarButtons(){
        return new JsonResult(true, "获取顶部工具栏按钮成功", ${modelVariableName}Service.getToolBarButtons());
    }

    @GetMapping("/getOperatingButtons")
    @ReadAccess(description = "")
    public JsonResult getOperatingButtons(){
        return new JsonResult(true, "获取操作按钮成功", ${modelVariableName}Service.getOperatingButtons());
    }

}
