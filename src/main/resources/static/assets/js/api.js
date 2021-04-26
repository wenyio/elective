function login(data) {
    return request("/api/user/login", { type: "POST", data });
}

function modifyPwd(data) {
    return request(`/api/user/modify/password?password=${data}`, { type: "POST" });
}

function listUser(data) {
    return request(`/api/user/list`, { type: "POST", data });
}

function deleteUserById(id) {
    return request(`/api/user/${id}`, { type: "DELETE" });
}

function changeStatus(id) {
    return request(`/api/user/${id}/status`, { type: "GET" });
}

function saveUser(data) {
    return request(`/api/user`, { type: "POST", data });
}

function listCourse(data) {
    return request(`/api/course/list`, { type: "POST", data });
}

function deleteCourseById(id) {
    return request(`/api/course/${id}`, { type: "DELETE" });
}

function saveCourse(data) {
    return request(`/api/course`, { type: "POST", data });
}

function settlement(id) {
    return request(`/api/course/${id}/settlement`, { type: "GET" });
}

function elective(id) {
    return request(`/api/course/${id}/elective`, { type: "GET" });
}

function userElective() {
    return request(`/api/user/elective`, { type: "GET" });
}

function electiveByCourseId(courseId) {
    return request(`/api/user/elective/${courseId}`, { type: "GET" });
}

function cancelElective(courseId) {
    return request(`/api/user/elective/${courseId}/cancel`, { type: "GET" });
}

function saveGrade(ucId, grade) {
    return request(`/api/user/course/${ucId}/grade/${grade}`, {type: "POST"})
}
