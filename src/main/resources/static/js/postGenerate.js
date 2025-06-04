
    function loadPosts() {
    fetch('/loadPosts')
        .then(res => res.json())
        .then(posts => {
            const container = document.getElementById('post-list');
            container.innerHTML = ''; // 清空

            posts.forEach(post => {
                const html = `
                <div id="${post.id.oid}" class="card mb-4 shadow p-3 mb-5 bg-body rounded" style="width: 100%; max-width: 1700px; margin: auto;">
                    <div class="row g-0">
                        <div id="dataBlock">
                            <nav class="navbar navbar-light bg-light px-3 py-4">
                                <h3>租屋資料</h3>
                            </nav>
                            <div class="card-body">
                                <table class="table table-hover">
                                    <tbody>
                                        <tr><th>地址</th><td>${post.address}</td></tr>
                                        <tr><th>租金</th><td>${post.rentalRange.minRental === post.rentalRange.maxRental ? `${post.rentalRange.minRental}元` : `${post.rentalRange.minRental} ~ ${post.rentalRange.maxRental}元`}</td></tr>
                                        <tr><th>租屋補助</th><td>${post.rentalSubsidy}</td></tr>
                                        <tr><th>坪數</th><td>${post.area}</td></tr>
                                        <tr><th>格局</th><td>${post.layout.livingRoom}廳 ${post.layout.bathroom}衛 ${post.layout.bedroom}房</td></tr>
                                        <tr><th>是否有頂樓加蓋</th><td>${post.hasRooftopAddOn}</td></tr>
                                        <tr><th>性別限制</th><td>${post.genderLimit.genderLimitText}</td></tr>
                                        <tr><th>寵物</th><td>${post.allowPet}</td></tr>
                                        <tr><th>養魚</th><td>${post.allowFish}</td></tr>
                                        <tr><th>開伙</th><td>${post.allowCook}</td></tr>
                                        <tr><th>電梯</th><td>${post.hasElevator}</td></tr>
                                        <tr><th>機車停車位</th><td>${post.hasScooterParking}</td></tr>
                                        <tr><th>汽車停車位</th><td>${post.hasCarParking}</td></tr>
                                        <tr>
                                            <th>聯絡方式</th>
                                            <td colspan="2">
                                                <div><span class="label">聯絡人：</span>${post.contactInfos[0]?.contactPerson || '未知'}</div>
                                                <div><span class="label">電話：</span>${post.contactInfos[0]?.phones[0] || '未知'}</div>
                                                <div><span class="label">Line Id：</span>${post.contactInfos[0]?.lineIds[0] || '未知'}</div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="d-flex justify-content-end mb-3" style="padding-right: 20px;">
                                <button class="btn btn-outline-secondary collect-btn" data-post-id="${post.id.oid}" data-collected="false" onclick="toggleCollect(this)">
                                    <i class="fa fa-heart-o"></i> <span>收藏</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                `;
                container.insertAdjacentHTML('beforeend', html);
            });
        })
        .catch(err => {
            console.error("載入貼文時發生錯誤:", err);
        });
}
