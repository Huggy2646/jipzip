import { createRouter, createWebHistory } from "vue-router";
import { upQnaPostHit, upNoticePostHit } from "@/api/board.js";
import MainView from "@/views/MainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/boardview",
      name: "boardview",
      component: () => import("@/views/BoardView.vue"),
      children: [
        {
          path: "qna",
          name: "qnaBoard",
          component: () => import("@/views/QnABoardView.vue"),
          children: [
            {
              path: "list",
              name: "qnaTable",
              component: () => import("@/components/board/BoardTable.vue"),
            },
            {
              path: "regist",
              name: "qnaPostRegist",
              component: () => import("@/components/board/PostRegist.vue"),
              meta: { requiresAuth: true }, // 인증이 필요한 라우트에 메타 필드 추가
            },
            {
              path: "posts/:postId",
              name: "qnaPost",
              component: () => import("@/views/PostView.vue"),
              children: [
                {
                  path: "",
                  name: "qnaPostWithComments",
                  component: () =>
                    import("@/components/board/PostWithComments.vue"),
                  beforeEnter: (to, from, next) => {
                    const success = (response) => {
                      if (response.status === 200) {
                        next();
                      } else next(false);
                    };

                    const fail = (error) => {
                      alert("오류 발생" + error);
                      next(false);
                    };
                    // TODO: 게시글을 수정하고 다시 게시글로 돌아올 때도 조회수가 1 높여짐. 고쳐야 함
                    upQnaPostHit(success, fail, to.params.postId); // 해당 게시물의 상세보기로 진입하기 전, 조회수 1 높이기
                  },
                },
                {
                  path: "modify",
                  name: "qnaPostModify",
                  component: () => import("@/components/board/PostModify.vue"),
                },
              ],
            },
          ],
        },
        {
          path: "notice",
          name: "noticeBoard",
          component: () => import("@/views/NoticeBoardView.vue"),
          children: [
            {
              path: "list",
              name: "noticeTable",
              component: () => import("@/components/board/BoardTable.vue"),
            },
            {
              path: "regist",
              name: "noticePostRegist",
              component: () => import("@/components/board/PostRegist.vue"),
              meta: { requiresAuth: true }, // 인증이 필요한 라우트에 메타 필드 추가
            },
            {
              path: "posts",
              name: "noticePost",
              component: () => import("@/views/PostView.vue"),
              children: [
                {
                  path: ":postId",
                  name: "noticePostWithComments",
                  component: () =>
                    import("@/components/board/PostWithComments.vue"),
                  beforeEnter: (to, from, next) => {
                    const success = (response) => {
                      if (response.status === 200) {
                        next();
                      } else next(false);
                    };

                    const fail = (error) => {
                      alert("오류 발생" + error);
                      next(false);
                    };

                    // TODO: 게시글을 수정하고 다시 게시글로 돌아올 때도 조회수가 1 높여짐. 고쳐야 함
                    upNoticePostHit(success, fail, to.params.postId); // 해당 게시물의 상세보기로 진입하기 전, 조회수 1 높이기
                  },
                },
                {
                  path: ":postId/modify",
                  name: "noticePostModify",
                  component: () => import("@/components/board/PostModify.vue"),
                },
              ],
            },
          ],
        },
      ],
    },
    {
      path: "/userinfo",
      name: "userInfo",
      component: () => import("@/views/UserView.vue"),
      redirect: "/userinfo/checkpassword",
      children: [
        {
          path: "checkpassword",
          name: "checkPassword",
          component: () => import("@/components/userinfo/CheckPassword.vue"),
        },
        {
          path: "viewuserinfo",
          name: "viewUserInfo",
          component: () => import("@/components/userinfo/ViewUserInfo.vue"),
          // url에 바로 접근했을 때 pw 확인 절차를 무조건 하게끔 만듦
          beforeEnter: (to, from, next) => {
            const isPasswordChecked = localStorage.getItem("isAuthenticated");
            if (isPasswordChecked == "true") {
              localStorage.removeItem("isAuthenticated");
              next();
            } else {
              next({ name: "checkPassword" });
            }
          },
        },
      ],
    },
  ],
});

// 네비게이션 가드 추가(로그인 여부 확인)
router.beforeEach((to, from, next) => {
  const loginUser = sessionStorage.getItem("id");

  if (to.matched.some((record) => record.meta.requiresAuth) && !loginUser) {
    // 인증이 필요한 라우트인데 로그인되어 있지 않은 경우
    alert("로그인이 필요합니다.");
    next({ name: "main" }); // 메인 페이지로 리디렉션
  } else {
    next(); // 계속 진행
  }
});

export default router;
