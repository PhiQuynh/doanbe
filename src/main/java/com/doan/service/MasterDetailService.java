package com.doan.service;

import com.doan.config.Constants;
import com.doan.dto.*;
import com.doan.entity.*;
import com.doan.payload.*;
import com.doan.repository.MasterDetailRepository;
import com.doan.repository.MasterRepository;
import com.doan.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterDetailService {

    @Autowired
    MasterRepository masterRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    MasterDetailRepository masterDetailRepository;

    public ResponseEntity<?> addMasterDetail(AddMasterDetailRequest addMasterDetailRequest) {
        try{
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.setMssv(addMasterDetailRequest.getMssv());
            masterDetail.setStudentName(addMasterDetailRequest.getStudentName());
            masterDetail.setStudentClass(addMasterDetailRequest.getStudentClass());
            masterDetail.setTitleNameEn(addMasterDetailRequest.getTitleNameEn());
            masterDetail.setTitleNameVn(addMasterDetailRequest.getTitleNameVn());
            masterDetail.setStatus(TeachershipStatus.PENDING);
            Master master = masterRepository.findById(addMasterDetailRequest.getMasterId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid MasterId: " + addMasterDetailRequest.getMasterId()));
            masterDetail.setMaster(master);
            Teacher teacher = teacherRepository.findById(addMasterDetailRequest.getTeacherHDId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid TeacherHDId: " + addMasterDetailRequest.getTeacherHDId()));
            masterDetail.setTeacherHD(teacher);
            masterDetailRepository.save(masterDetail);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_ADD), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_ADD), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editMasterDetail(EditMasterDetailRequest editMasterDetailRequest) {
        try{
            MasterDetail masterDetail = masterDetailRepository.findById(editMasterDetailRequest.getMasterDetailId())
                    .orElseThrow();
            masterDetail.setTitleNameVn(editMasterDetailRequest.getTitleNameVn());
            masterDetail.setTitleNameEn(editMasterDetailRequest.getTitleNameEn());
            Teacher teacher = teacherRepository.findById(editMasterDetailRequest.getTeacherHDId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid TeacherHDId: " + editMasterDetailRequest.getTeacherHDId()));
//            masterDetail.setTeacherHD(teacher);
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_EDIT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editScoreCoucil(EditScoreRequest editScoreCoucilRequest) {
        try{
            MasterDetail masterDetail = masterDetailRepository.findById(editScoreCoucilRequest.getMasterDetailId())
                    .orElseThrow();
            masterDetail.setScoreCoucil(editScoreCoucilRequest.getScore());
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_ADD_SCORE_COUCIL_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ADD_SCORE_COUCIL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editScoreArgument(EditScoreRequest editScoreCoucilRequest) {
        try{
            MasterDetail masterDetail = masterDetailRepository.findById(editScoreCoucilRequest.getMasterDetailId())
                    .orElseThrow();
            masterDetail.setScoreArgument(editScoreCoucilRequest.getScore());
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_ADD_SCORE_ARGUMENT_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ADD_SCORE_ARGUMENT_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> assignCriticism(AssignCriticismRequest assignCriticismRequest) {
        try{
            MasterDetail masterDetail = masterDetailRepository.findById(assignCriticismRequest.getMasterDetailId())
                    .orElseThrow();
            Teacher teacher = teacherRepository.findById(assignCriticismRequest.getTeacherPBId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid teacherId: " + assignCriticismRequest.getTeacherPBId()));
            masterDetail.setTeacherPB(teacher);
            masterDetailRepository.save(masterDetail);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_PCPB_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_PCPB_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getMasterDetailByGVHD(Optional<Teacher> teacher) {
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.findMasterDetailsByTeacherHD(teacher);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getMasterDetailByGVPB(Optional<Teacher> teacher) {
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.findMasterDetailsByTeacherPB(teacher);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getMasterDetailByScore1(Master master) {
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.findByScoreArgumentLessThanAndMaster(master);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalRecords(masterDetailByTeacherResponDTOS.size());
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getMasterDetailByScore() {
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.findByScoreArgumentLessThan();
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalRecords(masterDetailByTeacherResponDTOS.size());
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editByGvhd(EditMasterDetailByGVHDRequest editMasterDetailByGVHDRequest) {
        try{
            MasterDetail masterDetail = masterDetailRepository.findById(editMasterDetailByGVHDRequest.getMasterDetailId())
                    .orElseThrow();
            Teacher teacher = teacherRepository.findById(editMasterDetailByGVHDRequest.getTeacherHDId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid TeacherHDId: " + editMasterDetailByGVHDRequest.getTeacherHDId()));
            masterDetail.setTeacherHD(teacher);
            masterDetail.setStatus(TeachershipStatus.PENDING);
//            masterDetail.setStatusTeacher(editMasterDetailByGVHDRequest.getStatusTeacher());
            masterDetailRepository.save(masterDetail);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_EDIT_MASTER_DETAIL_BY_GVHD_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_EDIT_MASTER_DETAIL_BY_GVHD_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getSuccessfulDefenseStudents1(Master master) {
        List<MasterDetailDTO> masterDetailDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.getSuccessfulDefenseStudentsAndMaster(master);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailDTO = new MasterDetailDTO();
                masterDetailDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailDTO.setMssv(masterDetail.getMssv());
                masterDetailDTO.setStudentName(masterDetail.getStudentName());
                masterDetailDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
//                masterDetailDTO.setTeacherNameHd(masterDetail.getTeacherHD().getTeacherName());
                masterDetailDTO.setTeacherNamePb(masterDetail.getTeacherPB().getTeacherName());
                masterDetailDTOS.add(masterDetailDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalRecords(masterDetailDTOS.size());
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_SUCCCES);
            respon.setMasterDetails(masterDetailDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getSuccessfulDefenseStudents() {
        List<MasterDetailDTO> masterDetailDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.getSuccessfulDefenseStudents();
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailDTO = new MasterDetailDTO();
                masterDetailDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailDTO.setMssv(masterDetail.getMssv());
                masterDetailDTO.setStudentName(masterDetail.getStudentName());
                masterDetailDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
//                masterDetailDTO.setTeacherNameHd(masterDetail.getTeacherHD().getTeacherName());
                masterDetailDTO.setTeacherNamePb(masterDetail.getTeacherPB().getTeacherName());
                masterDetailDTOS.add(masterDetailDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalRecords(masterDetailDTOS.size());
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_SUCCCES);
            respon.setMasterDetails(masterDetailDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllMasterDetails() {
        List<MasterDetailDTO> masterDetailDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
//        try{
            List<MasterDetail> masterDetails = masterDetailRepository.findAll();
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailDTO = new MasterDetailDTO();
                masterDetailDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailDTO.setMssv(masterDetail.getMssv());
                masterDetailDTO.setStudentName(masterDetail.getStudentName());
                masterDetailDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailDTO.setMasterName(masterDetail.getMaster().getMasterName());
                }
                if(  masterDetail.getTeacherPB() != null) {
//                    masterDetailDTO.setTeacherNameHd(masterDetail.getTeacherHD().getTeacherName());
                    masterDetailDTO.setTeacherNamePb(masterDetail.getTeacherPB().getTeacherName());
                }
                masterDetailDTOS.add(masterDetailDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalRecords(masterDetailDTOS.size());
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_SUCCCES);
            respon.setMasterDetails(masterDetailDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
//        } catch (Exception e) {
//            respon.setCode(Constants.ERR_CODE);
//            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_Successful_Defense_ERR);
//            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

       @Transactional
    public  ResponseEntity<?> getStudent(Long teacher_hd_id){
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.listUserByUserId(teacher_hd_id);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                    masterDetailByTeacherResponDTO.setStartDate(masterDetail.getMaster().getStartDate());
                    masterDetailByTeacherResponDTO.setEndDate(masterDetail.getMaster().getEndDate());
                }
//               if(masterDetail.getStatus().equals("ACCEPTED")){
                   if(masterDetail.getTeacherHD() != null ){
                       masterDetailByTeacherResponDTO.setTeacherHD(masterDetail.getTeacherHD().getTeacherName());
                   }
//               }
                if(masterDetail.getTeacherPB() != null){
                    masterDetailByTeacherResponDTO.setTeacherPB(masterDetail.getTeacherPB().getTeacherName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

    @Transactional
    public  ResponseEntity<?> getStudentInvite(Long teacher_hd_id){
        List<MasterDetailDTO> masterDetailByTeacherResponDTOS = new ArrayList<>();
        GetMasterDetailRespon respon = new GetMasterDetailRespon();
        try{
            List<MasterDetail> masterDetails = masterDetailRepository.listStudentInviteTeacher(teacher_hd_id);
            for(MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO masterDetailByTeacherResponDTO = new MasterDetailDTO();
                masterDetailByTeacherResponDTO.setMasterDetailId(masterDetail.getMasterDetailId());
                masterDetailByTeacherResponDTO.setMssv(masterDetail.getMssv());
                masterDetailByTeacherResponDTO.setStudentName(masterDetail.getStudentName());
                masterDetailByTeacherResponDTO.setStudentClass(masterDetail.getStudentClass());
                masterDetailByTeacherResponDTO.setTitleNameVn(masterDetail.getTitleNameVn());
                masterDetailByTeacherResponDTO.setTitleNameEn(masterDetail.getTitleNameEn());
                masterDetailByTeacherResponDTO.setScoreArgument(masterDetail.getScoreArgument());
                masterDetailByTeacherResponDTO.setScoreCoucil(masterDetail.getScoreCoucil());
                if (masterDetail.getMaster() != null) {
                    masterDetailByTeacherResponDTO.setMasterName(masterDetail.getMaster().getMasterName());
                    masterDetailByTeacherResponDTO.setStartDate(masterDetail.getMaster().getStartDate());
                    masterDetailByTeacherResponDTO.setEndDate(masterDetail.getMaster().getEndDate());
                }
//               if(masterDetail.getStatus().equals("ACCEPTED")){
                if(masterDetail.getTeacherHD() != null ){
                    masterDetailByTeacherResponDTO.setTeacherHD(masterDetail.getTeacherHD().getTeacherName());
                }
//               }
                if(masterDetail.getTeacherPB() != null){
                    masterDetailByTeacherResponDTO.setTeacherPB(masterDetail.getTeacherPB().getTeacherName());
                }
                masterDetailByTeacherResponDTOS.add(masterDetailByTeacherResponDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDetails(masterDetailByTeacherResponDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @Transactional
    public GetStudent getMaterById(Long masterDetailId){
       return getMater(masterDetailRepository.findById(masterDetailId).orElseThrow());
   }

   public GetStudent getMater(MasterDetail masterDetail){
       GetStudent.GetStudentBuilder builder = GetStudent.builder()
               .mssv(masterDetail.getMssv())
               .studentClass(masterDetail.getStudentClass())
               .studentName(masterDetail.getStudentName())
               .titleNameVn(masterDetail.getTitleNameVn())
               .titleNameEn(masterDetail.getTitleNameEn())
               .matername(masterDetail.getMaster().getMasterName())
               .teacherName(masterDetail.getTeacherHD().getTeacherName())
               .startDate(masterDetail.getMaster().getStartDate())
               .endDate(masterDetail.getMaster().getEndDate());
                if(masterDetail.getTeacherPB() != null){
                    builder.teacherPBName((masterDetail.getTeacherPB().getTeacherName()));
                }
                if(masterDetail.getScoreCoucil() != null){
                    builder.scoreCoucil(masterDetail.getScoreCoucil());
                }
                if (masterDetail.getScoreArgument() != null){
                    builder.scoreArgument(masterDetail.getScoreArgument());
                }
                if(masterDetail.getCoucil() != null){
                    builder.councliName(masterDetail.getCoucil().getCoucilName());
                }
       return builder.build();
   }

    public ResponseEntity<?> findAllByDetails(String studentName, Long master_id, Integer limit, Integer offset) {
        List<GetMaterDetail> teacherDTOS = new ArrayList<>();

        int totalRecords = 0;
        int page = offset / limit;
        try {
            Page<MasterDetail> teacherPage;

            if (master_id == null || master_id.equals("")) {
                teacherPage = masterDetailRepository.findAll(PageRequest.of(page, limit));
            } else {
                Master master = masterRepository.findById(master_id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + master_id));
                teacherPage = masterDetailRepository.findAllByMater(studentName, master, PageRequest.of(page, limit));
            }

            List<MasterDetail> teachers = teacherPage.getContent();
            totalRecords = (int) teacherPage.getTotalElements();
            for (MasterDetail teacher : teachers) {
                GetMaterDetail teacherDTO = new GetMaterDetail();
                teacherDTO.setMasterDetailId(teacher.getMasterDetailId());
                teacherDTO.setStudentName(teacher.getStudentName());
                teacherDTO.setStudentClass(teacher.getStudentClass());
                teacherDTO.setScoreArgument(teacher.getScoreArgument());
                teacherDTO.setScoreCoucil(teacher.getScoreCoucil());
                teacherDTO.setMssv(teacher.getMssv());
                teacherDTO.setTitleNameVn(teacher.getTitleNameVn());
                teacherDTO.setTitleNameEn(teacher.getTitleNameEn());
                if(teacher.getTeacherPB() != null){
                    teacherDTO.setTeacherPBName(teacher.getTeacherPB().getTeacherName());
                }
                if(teacher.getMaster() != null){
                    teacherDTO.setMaterName(teacher.getMaster().getMasterName());
                }
//                if (teacher.getTeacherHD() != null) {
//                    teacherDTO.setTeacherHDName(teacher.getTeacherHD().getTeacherName());
//                }
                teacherDTOS.add(teacherDTO);
            }
            return new ResponseEntity<>(teacherDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_ALL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public MasterDetail acceptMasterDetails(Long masterDetailId, Long teacherHDid){
        MasterDetail masterDetail = masterDetailRepository.findById(masterDetailId)
                .orElseThrow(() -> new RuntimeException("MasterDetail not found with ID: " + masterDetailId));

        if (masterDetail.getTeacherHD() == null || !masterDetail.getTeacherHD().getTeacherId().equals(teacherHDid)) {
            throw new RuntimeException("TeacherHD not found with ID: " + teacherHDid);
        }

        masterDetail.setStatus(TeachershipStatus.ACCEPTED);
        return masterDetailRepository.save(masterDetail);
    }


    public MasterDetail updateStatusNotAccept(Long masterDetailId, Long teacherHDid) {
        MasterDetail masterDetail = masterDetailRepository.findById(masterDetailId)
                .orElseThrow(() -> new RuntimeException("MasterDetail not found with ID: " + masterDetailId));

        if (masterDetail.getTeacherHD() == null || !masterDetail.getTeacherHD().getTeacherId().equals(teacherHDid)) {
            throw new RuntimeException("TeacherHD not found with ID: " + teacherHDid);
        }

        masterDetail.setStatus(TeachershipStatus.NOTACCEPTED);
        return masterDetailRepository.save(masterDetail);
    }

}
