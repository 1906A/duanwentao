<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>
 import {treeData} from "../../mockDB";

 export default {
    name: "category",
    data() {
      return {
        isEdit:true,
        treeData:treeData
      }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
       // console.log(node);


      },
      handleEdit(node) {
        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");
        let id = node.id;
        alert(id)
        if(id==0){
            //新增
            this.$http.post('/item/category/add',node)
                .then((res)=>{
                        alert(res.data);
                        if(res.data=="SUCC"){
                            alert("添加成功");
                        }else if(res.data=="FALL"){
                            alert("添加失败")
                        }
                    window.location.reload();
                    }
                ).catch((error)=>{
                alert("请求失败")
            })
        }else{
            //修改
            this.$http.post('/item/category/update',node)
                .then((res)=>{
                    alert(res.data)
                    if(res.data =='SUCC'){
                        alert('修改成功')

                    }else if(res.data =='FALL'){
                        alert('修改失败')
                    }
                    window.location.reload();
                }).catch((err)=>{
                alert("请求失败")
            })
        }

      },
      handleDelete(id) {
        console.log("delete ... " + id)

         //  this.$http.get("/item/category/deleteById?id=" + id)
         //      .then((res) => {
         //          if (res.data == 'SUCC') {
         //              alert("删除成功")
         //          } else if (res.data = "FALL") {
         //              alert("删除失败")
         //          }
         //      }).catch((res) => {alert("请求失败")
         // });

          this.$http.get("/item/category/deleteById?",{params:{id:id}})
              .then((res) => {
                  if (res.data == 'SUCC') {
                      alert("删除成功")
                  } else if (res.data = "FALL") {
                      alert("删除失败")
                  }
              }).catch((res) => {
              alert("请求失败")
          })
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>
