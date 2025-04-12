export async function initializeCourses() {
    const response = await fetch("https://localhost:8080/initializeCourses");
}

export async function getAllValidCourses() {
    const response = await fetch("https://localhost:8080/getAllValidCourses");
    return response.json();
}