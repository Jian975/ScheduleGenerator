<script>
    import { onMount } from "svelte";
    import { initializeCourses, getAllValidCourses } from "./Utilities/Courses.js";

    let courses = [];

    onMount(async () => {
        initializeCourses();
        try {
            courses = await getAllValidCourses();
        } catch (error) {
            console.error("Failed to fetch courses:", error);
        }
    });
</script>

<h2>Available Courses</h2>
{#if courses.length > 0}
    <ul>
        {#each courses as course}
            <li>{course.name} - {course.instructor}</li>
        {/each}
    </ul>
{:else}
    <p>Loading courses...</p>
{/if}