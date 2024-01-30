import{r as se,k as ue,f as ie,a as re,u as ce,W as de,x as me,P as pe}from"./index-ec104b6c.js";import{e as D,d as H,u as ve,c as S,a as _,o as g,b as x,r as L,n as U,f as Z,k as K,g as f,h as fe,w as Y,I as _e,i as j,j as z,l as G,m as l,p as E,t as J,q as ye,s as ge,v as b,x as t,y as ke,H as R,z as he,A as xe,B as we,Z as F,C as be,D as Ve,_ as Ce}from"./index-6c7202c1.js";const $e=["src","alt"],Se=D(H({name:"LayAvatar",props:{src:null,size:{default:"md"},radius:{type:Boolean,default:!1},icon:{default:"layui-icon-username"},alt:null},setup(o){const u=o,r=ve(),a=S(()=>["layui-avatar",u.radius?"layui-avatar-radius":"",u.size?`layui-avatar-${u.size}`:""]);return(c,y)=>_(r).default?(g(),x("span",{key:0,class:U(_(a))},[L(c.$slots,"default")],2)):(g(),x("span",{key:1,class:U(_(a))},[o.src?(g(),x("img",{key:0,src:o.src,alt:o.alt},null,8,$e)):(g(),Z(_(K),{key:1,type:o.icon},null,8,["type"]))],2))}})),ze={class:"layui-textarea-wrapper"},Be=["rows","cols","value","placeholder","name","disabled","maxlength"],Fe={key:0,class:"layui-textarea-clear"},He={key:1,class:"layui-texterea-count"},Te=D(H({name:"LayTextarea",props:{modelValue:null,name:null,placeholder:null,disabled:{type:Boolean},showCount:{type:Boolean},allowClear:{type:Boolean},cols:null,rows:null,maxlength:null,autosize:{type:[Boolean,Object]}},emits:["blur","input","update:modelValue","change","focus","clear"],setup(o,{expose:u,emit:r}){const a=o,c=f(null),y=f(!1),V=fe(),n=f("auto"),v=S(()=>({width:a.cols?"":"100%",height:a.rows?"":n.value,minHeight:a.rows?"":"100px"})),w=S(()=>Object.assign(v.value,V.style)),T=function(s){const e=s.target;y.value||(r("update:modelValue",e.value),j(()=>{r("input",e.value)}))},I=function(s){r("focus",s)},q=function(s){r("blur",s)},N=s=>{const e=s.target;r("change",e.value)},O=function(){r("update:modelValue",""),r("clear")},A=()=>{y.value=!0},B=s=>{y.value=!1,T(s)},M=S(()=>{var s;return((s=a.modelValue)==null?void 0:s.length)>0}),P=S(()=>{var s,e;let i=String((e=(s=a.modelValue)==null?void 0:s.length)!=null?e:0);return a.maxlength&&(i+="/"+a.maxlength),i});return Y([()=>a.modelValue,c],()=>{var s;if(!c.value||!a.autosize)return;const e=((s=c.value)==null?void 0:s.scrollHeight)+2;if(_e(a.autosize)){const{minHeight:i,maxHeight:h}=a.autosize;if(e<i||e>h)return}n.value="1px",j(()=>{var i;n.value=`${((i=c.value)==null?void 0:i.scrollHeight)+2}px`})},{immediate:!0}),u({focus:()=>{j(()=>{var s;(s=c.value)==null||s.focus()})},blur:()=>{j(()=>{var s;(s=c.value)==null||s.blur()})}}),(s,e)=>(g(),x("div",ze,[z("textarea",{ref_key:"textareaRef",ref:c,class:U(["layui-textarea",{"layui-textarea-disabled":o.disabled}]),rows:o.rows,cols:o.cols,value:o.modelValue,placeholder:o.placeholder,name:o.name,disabled:o.disabled,maxlength:o.maxlength,style:G(_(w)),onCompositionstart:A,onCompositionend:B,onInput:T,onFocus:I,onChange:N,onBlur:q},null,46,Be),o.allowClear&&_(M)?(g(),x("span",Fe,[l(_(K),{type:"layui-icon-close-fill",onClick:O})])):E("",!0),o.showCount?(g(),x("div",He,J(_(P)),1)):E("",!0)]))}})),je={class:"layui-collapse"},Le=D(H({name:"LayCollapse",props:{accordion:{type:Boolean,default:!1},modelValue:{default:()=>[]},collapseTransition:{type:Boolean,default:!0}},emits:["update:modelValue","change"],setup(o,{emit:u}){const r=o;Y(()=>r.modelValue,c=>{a.value=[].concat(c)});const a=f([].concat(r.modelValue));return ye("layCollapse",{accordion:r.accordion,collapseTransition:r.collapseTransition,activeValues:a,emit:u}),(c,y)=>(g(),x("div",je,[L(c.$slots,"default")]))}})),Ue={class:"layui-colla-item"},De={key:0},Ie={class:"layui-colla-content"},qe=D(H({name:"LayCollapseItem",props:{id:null,title:null,disabled:{type:Boolean,default:!1}},setup(o){const u=o,{accordion:r,activeValues:a,emit:c,collapseTransition:y}=ge("layCollapse");let V=S(()=>a.value.includes(u.id));const n=function(){if(u.disabled)return;const v=V.value;r?a.value=v?[]:[u.id]:v?a.value.splice(a.value.indexOf(u.id),1):a.value.push(u.id),c("update:modelValue",r?a.value[0]||null:a.value),c("change",u.id,!v,a.value)};return(v,w)=>(g(),x("div",Ue,[z("h2",{class:U(["layui-colla-title",{"layui-disabled":o.disabled}]),onClick:n},[L(v.$slots,"title",{props:u},()=>[b(J(o.title),1)]),z("i",{class:"layui-icon layui-colla-icon layui-icon-right",style:G({transform:_(V)?"rotate(90deg)":"none",transition:_(y)?"all 0.2s ease 0s":""})},null,4)],2),l(ke,{enable:_(y)},{default:t(()=>[_(V)?(g(),x("div",De,[z("div",Ie,[z("p",null,[L(v.$slots,"default",{props:u})])])])):E("",!0)]),_:3},8,["enable"])]))}}));const Ne=function(o){return R.get("/auth/token",o)},Oe=function(o){return R.get("/parser/video/search",o)},Ae=function(o){return R.get("/man/comment/page",o)},Me={class:"table-box"},Pe=H({__name:"index",setup(o){const u=he(),r=f(["2"]),a=xe({current:u.page,limit:u.limit,total:0}),c=f(),y=f(),V=f(),n=f({cookies:"",video:"",comments:[],comment:""}),v=f([]),w=f(!1),T=f([{title:"头像",width:"50px",key:"avatar",customSlot:"avatar"},{title:"昵称",width:"80px",key:"nickname"},{title:"抖音号",width:"80px",key:"sid"},{title:"评论内容",width:"300px",key:"text"},{title:"发表地区",width:"80px",key:"area"},{title:"发表时间",width:"120px",key:"released"},{title:"操作",width:"150px",customSlot:"operator",key:"operator",fixed:"right"}]);we(()=>{u.cookies==""?r.value=["1","2"]:(n.value.cookies=u.cookies,r.value=["2"])});const I=async()=>{c.value.validate(async(e,i,h)=>{if(!e)return;let d={cookies:n.value.cookies},{data:p,code:k,msg:C}=await Ne(d);k==200?(u.token=p.token,u.cookies=n.value.cookies,r.value=["2"]):F.msg(C,{icon:2})})},q=async()=>{y.value.validate(async(e,i,h)=>{if(!e)return;if(n.value.cookies==""){F.msg("Cookies未设置",{icon:2});return}a.current=u.page,a.total=0,w.value=!0;let d={keyword:n.value.video,cookies:n.value.cookies},{code:p,msg:k}=await Oe(d);p==200?(a.total=0,v.value=[],F.msg(k)):F.msg(k,{icon:2}),w.value=!1})},N=()=>{n.value.video=""},O=()=>{a.current=u.page,B()},A=()=>{n.value.comments=[],n.value.comment="",a.current=u.page,B()},B=async()=>{w.value=!0;let e="";n.value.comments.forEach(C=>{e+=C+","}),e.endsWith(",")&&n.value.comment!=""?e+=n.value.comment:e.endsWith(",")&&(e=e.substring(0,e.length-1));let i={page:a.current,limit:a.limit,keyword:e==""?n.value.comment:e,date:s(new Date)},{data:h,code:d,msg:p,totals:k}=await Ae(i);d==200?(v.value=h,a.total=k):F.msg(p,{icon:2}),w.value=!1},M=e=>{window.open(e.suid,"_blank")},P=e=>{window.open(e.vid,"_blank")},s=e=>{let i=e.getFullYear(),h=(e.getMonth()+1).toString().padStart(2,"0"),d=e.getDate().toString().padStart(2,"0");return e.getHours().toString().padStart(2,"0"),e.getMinutes().toString().padStart(2,"0"),e.getSeconds().toString().padStart(2,"0"),i+"-"+h+"-"+d};return(e,i)=>{const h=Te,d=ue,p=be,k=ie,C=qe,Q=Ve,$=re,W=ce,X=de,ee=Le,ae=me,le=Se,te=pe,oe=se;return g(),Z(oe,{fluid:"true",class:"user-box"},{default:t(()=>[l(ae,null,{default:t(()=>[l(ee,{modelValue:r.value,"onUpdate:modelValue":i[4]||(i[4]=m=>r.value=m)},{default:t(()=>[l(C,{title:"Cookies设置",id:"1"},{default:t(()=>[l(k,{model:n.value,ref_key:"cookiesForm",ref:c,required:""},{default:t(()=>[l(d,{label:"Cookies","label-width":"90",prop:"cookies"},{default:t(()=>[l(h,{modelValue:n.value.cookies,"onUpdate:modelValue":i[0]||(i[0]=m=>n.value.cookies=m),placeholder:"请输入..."},null,8,["modelValue"])]),_:1}),l(d,{"label-width":"90"},{default:t(()=>[l(p,{style:{"margin-left":"105px"},type:"danger",size:"sm",onClick:I},{default:t(()=>[b(" 确定 ")]),_:1})]),_:1})]),_:1},8,["model"])]),_:1}),l(C,{title:"关键词搜索",id:"2",disabled:""},{default:t(()=>[l(W,null,{default:t(()=>[l($,{md:10},{default:t(()=>[l(k,{model:n.value,ref_key:"videoForm",ref:y,required:""},{default:t(()=>[l(W,null,{default:t(()=>[l($,{md:10},{default:t(()=>[l(d,{label:"视频关键词","label-width":"90",prop:"video"},{default:t(()=>[l(Q,{modelValue:n.value.video,"onUpdate:modelValue":i[1]||(i[1]=m=>n.value.video=m),placeholder:"请输入...",size:"sm","allow-clear":!0,style:{width:"98%"}},null,8,["modelValue"])]),_:1})]),_:1}),l($,{md:6},{default:t(()=>[l(d,{"label-width":"20"},{default:t(()=>[l(p,{style:{"margin-left":"20px"},type:"danger",size:"sm",onClick:q},{default:t(()=>[b(" 检索视频 ")]),_:1}),l(p,{size:"sm",onClick:N},{default:t(()=>[b(" 重置 ")]),_:1})]),_:1})]),_:1})]),_:1})]),_:1},8,["model"])]),_:1}),l($,{md:14},{default:t(()=>[l(k,{model:n.value,ref_key:"commentForm",ref:V},{default:t(()=>[l(W,null,{default:t(()=>[l($,{md:10},{default:t(()=>[l(d,{label:"评论关键词","label-width":"90",prop:"comments"},{default:t(()=>[l(X,{modelValue:n.value.comments,"onUpdate:modelValue":i[2]||(i[2]=m=>n.value.comments=m),inputValue:n.value.comment,"onUpdate:inputValue":i[3]||(i[3]=m=>n.value.comment=m),placeholder:"请输入...",size:"sm","allow-clear":!0},null,8,["modelValue","inputValue"])]),_:1})]),_:1}),l($,{md:6},{default:t(()=>[l(d,{"label-width":"20"},{default:t(()=>[l(p,{style:{"margin-left":"20px"},type:"primary",size:"sm",onClick:O},{default:t(()=>[b(" 检索评论 ")]),_:1}),l(p,{size:"sm",onClick:A},{default:t(()=>[b(" 重置 ")]),_:1})]),_:1})]),_:1})]),_:1})]),_:1},8,["model"])]),_:1})]),_:1})]),_:1})]),_:1},8,["modelValue"])]),_:1}),z("div",Me,[l(te,{page:a,height:"100%",columns:T.value,loading:w.value,"data-source":v.value,onChange:B},{avatar:t(({row:m})=>[l(le,{src:m.avatar},null,8,["src"])]),operator:t(({row:m})=>[l(p,{size:"xs",type:"warm",onClick:ne=>M(m)},{default:t(()=>[b("感兴趣")]),_:2},1032,["onClick"]),l(p,{size:"xs",type:"normal",onClick:ne=>P(m)},{default:t(()=>[b("视频链接")]),_:2},1032,["onClick"])]),_:1},8,["page","columns","loading","data-source"])])]),_:1})}}});const Re=Ce(Pe,[["__scopeId","data-v-3b651dfe"]]);export{Re as default};