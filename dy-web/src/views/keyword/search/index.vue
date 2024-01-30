<template>
  <lay-container fluid="true" class="user-box">
    <lay-card>
      <!-- <lay-form style="margin-top: 10px" required> -->
      <lay-collapse v-model="openLayCardKeys">
        <lay-collapse-item title="Cookies设置" id="1">
          <lay-form :model="searchParams" ref="cookiesForm" required>
            <lay-form-item label="Cookies" label-width="90" prop="cookies">
              <lay-textarea v-model="searchParams.cookies" placeholder="请输入..."></lay-textarea>
            </lay-form-item>
            <lay-form-item label-width="90">
              <lay-button style="margin-left: 105px" type="danger" size="sm" @click="confirmCookies">
                确定
              </lay-button>
            </lay-form-item>
          </lay-form>
        </lay-collapse-item>
        <lay-collapse-item title="关键词搜索" id="2" disabled>
          <lay-row>
            <lay-col :md="10">
              <lay-form :model="searchParams" ref="videoForm" required>
                <lay-row>
                  <lay-col :md="10">
                    <lay-form-item label="视频关键词" label-width="90" prop="video">
                      <lay-input v-model="searchParams.video" placeholder="请输入..." size="sm" :allow-clear="true"
                        style="width: 98%"></lay-input>
                    </lay-form-item>
                  </lay-col>
                  <lay-col :md="6">
                    <lay-form-item label-width="20">
                      <lay-button style="margin-left: 20px" type="danger" size="sm" @click="searchVideo">
                        检索视频
                      </lay-button>
                      <lay-button size="sm" @click="resetVideo"> 重置 </lay-button>
                    </lay-form-item>
                  </lay-col>
                </lay-row>
              </lay-form>
            </lay-col>
            <lay-col :md="14">
              <lay-form :model="searchParams" ref="commentForm">
                <lay-row>
                  <lay-col :md="10">
                    <lay-form-item label="评论关键词" label-width="90" prop="comments">
                      <lay-tag-input v-model="searchParams.comments" v-model:inputValue="searchParams.comment"
                        placeholder="请输入..." size="sm" :allow-clear="true"></lay-tag-input>
                    </lay-form-item>
                  </lay-col>
                  <lay-col :md="6">
                    <lay-form-item label-width="20">
                      <lay-button style="margin-left: 20px" type="primary" size="sm" @click="searchComment">
                        检索评论
                      </lay-button>
                      <lay-button size="sm" @click="resetComment"> 重置 </lay-button>
                    </lay-form-item>
                  </lay-col>
                </lay-row>
              </lay-form>
            </lay-col>
          </lay-row>
        </lay-collapse-item>
      </lay-collapse>
      <!-- </lay-form> -->
    </lay-card>
    <!-- table -->
    <div class="table-box">
      <lay-table :page="pagenation" :height="'100%'" :columns="columns" :loading="loading" :data-source="dataSource"
        @change="page">
        <!-- 头像插槽 -->
        <template #avatar="{ row }">
          <lay-avatar :src="row.avatar"></lay-avatar>
        </template>
        <!-- 操作 -->
        <template v-slot:operator="{ row }">
          <lay-button size="xs" type="warm" @click="attracted(row)">感兴趣</lay-button>
          <lay-button size="xs" type="normal" @click="linkVideo(row)">视频链接</lay-button>
        </template>
      </lay-table>
    </div>
  </lay-container>
</template>
<script setup lang="ts">
import { tokens, searchs, pages } from '../../../api/module/commone'
import { ref, reactive, onMounted } from 'vue'
import { layer } from '@layui/layui-vue'
import { useUserStore } from '../../../store/user'
const userStore = useUserStore()
const openLayCardKeys = ref(["2"])
const pagenation = reactive({ current: userStore.page, limit: userStore.limit, total: 0 })
const cookiesForm = ref()
const videoForm = ref()
const commentForm = ref()
const searchParams = ref({
  cookies: '',
  video: '',
  comments: [],
  comment: '',
})
const dataSource = ref<any>([])
const loading = ref(false)
const columns = ref([
  { title: '头像', width: '50px', key: 'avatar', customSlot: 'avatar' },
  { title: '昵称', width: '80px', key: 'nickname' },
  { title: '抖音号', width: '80px', key: 'sid' },
  { title: '评论内容', width: '300px', key: 'text' },
  { title: '发表地区', width: '80px', key: 'area' },
  { title: '发表时间', width: '120px', key: 'released' },
  { title: '操作', width: '150px', customSlot: 'operator', key: 'operator', fixed: 'right' }
])
onMounted(() => {
  if (userStore.cookies == "") {
    openLayCardKeys.value = ["1", "2"]
  } else {
    searchParams.value.cookies = userStore.cookies
    openLayCardKeys.value = ["2"]
  }
})
const confirmCookies = async () => {
  cookiesForm.value.validate(async (isValidate: boolean, model: any, errors: any) => {
    if (!isValidate) {
      return
    }
    let params = {
      cookies: searchParams.value.cookies,
    }
    let { data, code, msg } = await tokens(params)
    if (code == 200) {
      userStore.token = data.token
      userStore.cookies = searchParams.value.cookies
      openLayCardKeys.value = ["2"]
    } else {
      layer.msg(msg, { icon: 2 })
    }
  })
}
const searchVideo = async () => {
  videoForm.value.validate(async (isValidate: boolean, model: any, errors: any) => {
    if (!isValidate) {
      return
    }
    if (searchParams.value.cookies == "") {
      layer.msg("Cookies未设置", { icon: 2 })
      return
    }
    pagenation.current = userStore.page
    pagenation.total = 0
    loading.value = true
    let params = {
      keyword: searchParams.value.video,
      cookies: searchParams.value.cookies,
    }
    let { code, msg } = await searchs(params)
    if (code == 200) {
      pagenation.total = 0
      dataSource.value = []
      layer.msg(msg)
    } else {
      layer.msg(msg, { icon: 2 })
    }
    loading.value = false
  })
}
const resetVideo = () => {
  searchParams.value.video = ''
}
const searchComment = () => {
  // commentForm.value.validate(async (isValidate: boolean, model: any, errors: any) => {
  //   if (!isValidate) {
  //     return
  //   }
  //   pagenation.current = userStore.page
  //   page()
  // })
  pagenation.current = userStore.page
  page()
}
const resetComment = () => {
  searchParams.value.comments = []
  searchParams.value.comment = ''
  pagenation.current = userStore.page
  page()
}

const page = async () => {
  loading.value = true
  let tempComment = ''
  searchParams.value.comments.forEach((item) => {
    tempComment += item + ","
  });
  if (tempComment.endsWith(",") && searchParams.value.comment != '') {
    tempComment += searchParams.value.comment
  } else if (tempComment.endsWith(",")) {
    tempComment = tempComment.substring(0, tempComment.length - 1)
  }
  let params = {
    page: pagenation.current,
    limit: pagenation.limit,
    keyword: tempComment == '' ? searchParams.value.comment : tempComment,
    date: localDate(new Date())
  }
  let { data, code, msg, totals } = await pages(params)
  if (code == 200) {
    dataSource.value = data
    pagenation.total = totals
  } else {
    layer.msg(msg, { icon: 2 })
  }
  loading.value = false
}

const attracted = (row: any) => {
  window.open(row.suid, "_blank");
}
const linkVideo = (row: any) => {
  window.open(row.vid, "_blank");
}
const localDate = (date: Date) => {
  let year = date.getFullYear(); // 年
  let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月
  let day = date.getDate().toString().padStart(2, '0'); // 日
  let hour = date.getHours().toString().padStart(2, '0'); // 时
  let minute = date.getMinutes().toString().padStart(2, '0'); // 分
  let second = date.getSeconds().toString().padStart(2, '0'); // 秒
  return year + "-" + month + "-" + day;
}
</script>

<style scoped>
.user-box {
  height: calc(100vh - 110px);
  margin-top: 10px;
  box-sizing: border-box;
  overflow: hidden;
}

.top-search {
  margin-top: 10px;
  padding: 10px;
  height: 40px;
  border-radius: 4px;
  background-color: #fff;
}

.table-box {
  margin-top: 10px;
  padding: 10px;
  height: 600px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
}

.search-input {
  display: inline-block;
  width: 98%;
  margin-right: 10px;
}

.isChecked {
  display: inline-block;
  background-color: #e8f1ff;
  color: red;
}
</style>