"use client";

import { useRouter, usePathname } from "next/navigation";
import { useEffect, useState } from "react";
import api from "@/lib/axios";
import SideNavbar from "../../../../../components/lesson/sidebar";
import Puzzle from "../../../../../components/lesson/puzzle";
import PuzzleInfo from "../../../../../components/lesson/puzzleInfo";

export default function CategorySelectPage() {
  const pathname = usePathname();
  const router = useRouter();
  const [locationId, setLocationId] = useState<number | null>(null);
  const [categoryId, setCategoryId] = useState<number | null>(null);
  const [categoryName, setCategoryName] = useState<string>("not yet");
  const [selectedPuzzleId, setSelectedPuzzleId] = useState<number | null>(null);
  const [selectedPuzzleAccuracy, setSelectedPuzzleAccuracy] = useState<number | null>(null);
  const [categoryProgress, setCategoryProgress] = useState<number>(0);
  const [eachLessonProgress, setEachLessonProgress] = useState<object | null>(null);

  // 선택된 지역, 카테고리 할당
  useEffect(() => {
    const pathSegments = pathname.split("/");
    const selectedLocation = parseInt(
      pathSegments[pathSegments.length - 2],
      10,
    );
    const selectedCategory = parseInt(
      pathSegments[pathSegments.length - 1],
      10,
    );

    if (
      ![1, 2, 3].includes(selectedLocation) ||
      Number.isNaN(selectedCategory)
    ) {
      router.push("/lesson/1/1");
    } else {
      setLocationId(selectedLocation);
      setCategoryId(selectedCategory);
    }
  }, [pathname, router]);

  useEffect(() => {
    if (locationId !== null && categoryId !== null) {
      // 현재 카테고리 이름 할당
      api
        .get(
          `learn/lesson-group?locationId=${locationId}&categoryId=${categoryId}`,
        )
        .then(response => {
          if (response.status === 200) {
            if (
              response.data.length > 0 && response.data[0].lessonCategoryName
            ) {
              setCategoryName(response.data[0].lessonCategoryName);
            }
          }
        })
        .catch(error => {
          console.error("API 요청 중 오류 발생:", error);
        });
      // 카테고리별 진척도, 레슨그룹 당 popover 에 표시할 lessonGroupId, groupProgress, avgAcuuracy 가져오기

      // api.get(`learn/lesson-group/progress?locationId=${locationId}&categoryId=${categoryId}`)
      // .then(response=>{
      //   if (response.status === 200){
      //     setCategoryProgress(response.data.progress)
      //     setEachLessonProgress(response.data.lessonGroup)
      //   }
      // })
    }
  }, [locationId, categoryId]);

  // 새로운 onSelect 함수 구현
  const handlePuzzleSelect = (pieceId: number, avgAccuracy: number) => {
    setSelectedPuzzleId(pieceId);
    setSelectedPuzzleAccuracy(avgAccuracy);
  };

  return (
    <div className="flex flex-col justify-center items-center">
      <div className="flex w-full justify-center items-center">
        <div className="w-1/5 bg-gray-100 p-4">
          <h2>locationID = {locationId}</h2>
          <h2>CategoryName = {categoryName}</h2>
          <SideNavbar location={locationId} />
        </div>
        <div className="flex-none flex flex-col items-center justify-center p-4">
          {/* 태훈형이 만들어준 퍼즐 조각 넣을 예정 */}
          {locationId && (
            <Puzzle
              id={locationId}
              totalProgress={categoryProgress}
              onSelect={handlePuzzleSelect} // 새로운 onSelect 핸들러 사용
              eachLessonProgress={eachLessonProgress}
            />
          )}
        </div>
        <div className="w-1/5 p-4 flex items-center">
          {selectedPuzzleId !== null && selectedPuzzleAccuracy !== null && (
            <PuzzleInfo
              locationId={locationId}
              id={selectedPuzzleId}
              avgAccuracy={selectedPuzzleAccuracy} // avgAccuracy 전달
            />
          )}
        </div>
      </div>
    </div>
  );
}
