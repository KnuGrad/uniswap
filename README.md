# uniswap-backend

## 브랜치 전략
모든 브랜치는 Pull Request로 코드 리뷰를 진행한 후 merge 합니다.

* feature 브랜치: 기능 개발을 합니다.
* develop 브랜치: 개발이 완료된 feature 브랜치를 이 브랜치에 merge 합니다.
* main 브랜치: 배포 브랜치로, 배포를 진행할 때 develop에서 main으로 merge합니다. merge 시 CI/CD 자동배포 프로세스가 진행됩니다.
  
### 커밋 히스토리 관리
* develop <- feature merge 시 **Squash & Merge** 방식 사용합니다.
* main <- develop merge 시 **Rebase & Merge** 방식을 사용합니다.

### 개발 프로세스 및 PR 규칙
1. develop 브랜치 fetch & merge 또는 pull로 최신화합니다.
2. develop 브랜치를 base로 feature/#이슈번호 브랜치 생성 및 개발을 진행합니다.
3. 개발이 완료되면 rebase 등을 통해 커밋 히스토리(로그)를 깔끔하게 정리합니다.
4. feature 브랜치 push 및 PR 요청합니다.
5. 모든 PR은 코드 리뷰가 진행되어야 하고, 리뷰어에게 `Approve`를 받아야 merge가 가능합니다.

---
